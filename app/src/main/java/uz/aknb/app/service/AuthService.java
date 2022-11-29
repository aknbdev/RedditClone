package uz.aknb.app.service;

import db.entity.EntUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import security.service.JwtProvider;
import uz.aknb.app.dto.AuthenticationResponse;
import uz.aknb.app.dto.LoginRequest;
import uz.aknb.app.dto.RegisterRequest;
import uz.aknb.app.exception.RedditException;
import uz.aknb.app.mapper.UserMapper;
import db.repository.UserRepository;
import javax.transaction.Transactional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final VerifyTokenService verifyTokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final JwtProvider jwtProvider;
    private final UserMapper mapper;

    public AuthService(AuthenticationManager authenticationManager,
                       VerifyTokenService verifyTokenService,
                       PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       MailService mailService,
                       JwtProvider jwtProvider,
                       UserMapper mapper) {
        this.authenticationManager = authenticationManager;
        this.verifyTokenService = verifyTokenService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.jwtProvider = jwtProvider;
        this.mapper = mapper;
    }

    @Transactional
    public void signup(RegisterRequest request){

        String token;
        EntUser user = getByEmail(request.getEmail());
        if (user != null) {

            if (passwordEncoder.matches(request.getPassword(), user.getPassword()) && !user.getVerified()) {
                mapper.update(user, request);
            } else {
                throw new RedditException("User already exists");
            }
        } else {

            user = mapper.fromRegisterRequestToEntUser(request);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepository.save(user);
        token = verifyTokenService.create(user);
        mailService.sendVerifyMail(user.getEmail(), token);
    }

    public void verifyMail(String token) {
        EntUser user = verifyTokenService.isValidToken(token);
        enableUser(user);
    }

    @Transactional
    public void enableUser(EntUser user) {
        user.setVerified(true);
        userRepository.save(user);
    }

    protected EntUser getByEmail(String email) {
        return userRepository.findByEmail(email).orElseGet(()-> null);
    }

    public AuthenticationResponse login(LoginRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),
                        request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(token, request.getEmail());
    }
}
