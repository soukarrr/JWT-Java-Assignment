package com.example.ExampleJWT.service;

import com.example.ExampleJWT.dto.GeneralResponse;
import com.example.ExampleJWT.dto.admin.AdminDto;
import com.example.ExampleJWT.entity.Admin;
import com.example.ExampleJWT.entity.Token;
import com.example.ExampleJWT.mapper.AdminMapper;
import com.example.ExampleJWT.repository.AdminRepository;
import com.example.ExampleJWT.repository.TokenRepository;
import com.example.ExampleJWT.security.JwtUtil;
import com.example.ExampleJWT.validation.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AdminService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private TokenRepository tokenRepository;
    private AdminService adminService;
    private AdminMapper adminMapper;
    public ResponseEntity<GeneralResponse> createJwtToken(LoginRequest loginRequest) throws Exception
    {
        String userName = loginRequest.getUsername();
        String Password = loginRequest.getPassword();
        authenticate(userName,Password);
        UserDetails userDetails = loadUserByUsername(userName);

        String newGeneratedToken = jwtUtil.generateToken(userDetails);
        Admin admin = adminRepository.findByUsernameIgnoreCase(userName);
        revokeAllUserTokens(admin);
        saveAdminToken(admin,newGeneratedToken);
        AdminDto adminDto = getAdmin(userName);
        return new GeneralResponse().response(adminDto,newGeneratedToken);

    }
    public AdminDto getAdmin(String userName)
    {
        return adminMapper.INSTANCE.adminDtoMapper(adminRepository.findByUsernameIgnoreCase(userName));
    }
    private void revokeAllUserTokens(Admin admin)
    {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByAdmin(admin.getAdminId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t ->
        {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveAdminToken(Admin admin, String jwtToken)
    {
        Token tokenAdmin = Token.builder()
                .admin(admin)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(tokenAdmin);
    }

    private Authentication authenticate(String userName, String userPassword) throws Exception {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
            return authentication;
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsernameIgnoreCase(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (admin != null)
            return new org.springframework.security.core.userdetails.User
                    (admin.getUsername(), admin.getPassword(), authorities);
        else
            throw new UsernameNotFoundException("Admin not found with username: " +username);
    }

    public void initializeAdmin ()
    {
        Admin admin = new Admin();

            admin.setUsername("Admin");
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode("p@ssw0rd");
            admin.setPassword(encodedPassword);
            adminRepository.save(admin);

    }
}
