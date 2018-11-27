package com.victor.cursospring.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.victor.cursospring.domain.Cidade;
import com.victor.cursospring.domain.Cliente;
import com.victor.cursospring.domain.Endereco;
import com.victor.cursospring.domain.enums.Perfil;
import com.victor.cursospring.domain.enums.TipoCliente;
import com.victor.cursospring.dto.ClienteDTO;
import com.victor.cursospring.dto.ClienteNewDTO;
import com.victor.cursospring.repositories.ClienteRepository;
import com.victor.cursospring.repositories.EnderecoRepository;
import com.victor.cursospring.security.UserSS;
import com.victor.cursospring.services.exceptions.AuthorizationException;
import com.victor.cursospring.services.exceptions.DataIntegrityException;
import com.victor.cursospring.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
    
    @Autowired
    private BCryptPasswordEncoder pe;
    
    @Autowired
    private ClienteRepository repo;
    
    @Autowired
    private EnderecoRepository enderecoRepo;
    
    @Autowired
    private S3Service s3Service;
    
    public Cliente find(Integer id) {
        
        UserSS user = UserService.authenticated();
        if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado!");
        }
        
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
        + Cliente.class.getName()));
    }
    
    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = repo.save(obj);
        enderecoRepo.saveAll(obj.getEnderecos());
        return obj;
    }
    
    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir quando há outras entidades relacionadas");
        }
    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }
    
    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDTO(@Valid ClienteDTO objDto) {
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
    }
    
    public Cliente fromDTO(@Valid ClienteNewDTO objDto) {
        Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfCnpj(),
                TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
        Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
        Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), cid,
                objDto.getBairro(), objDto.getCep(), cli);

        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());
        
        if (objDto.getTelefone2() != null) {
            cli.getTelefones().add(objDto.getTelefone2());
        }
        
        if (objDto.getTelefone3() != null) {
            cli.getTelefones().add(objDto.getTelefone3());
        }
        
        return cli;
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
    
    public URI uploadProfilePicture(MultipartFile multiPartFile) {
        UserSS user = UserService.authenticated();
        
        if(user != null) {
            throw new AuthorizationException("Acesso negado");
        }
        
        URI uri = s3Service.uploadFile(multiPartFile);
        Cliente cli = find(user.getId());
        cli.setImageUrl(uri.toString());
        repo.save(cli);
        
        return uri;
    }
    
}
