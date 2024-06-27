package lt.almantas.ad.runner;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.almantas.ad.model.Entity.AdCategory;
import lt.almantas.ad.model.Entity.Roles;
import lt.almantas.ad.model.Entity.User;
import lt.almantas.ad.repositories.CategoryRepository;
import lt.almantas.ad.repositories.UserRepository;
import lt.almantas.ad.security.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty() && userRepository.findByUsername("user").isEmpty()) {
            User adminUser = new User();
            User normalUser = new User();

            adminUser.setRoleID(Roles.ADMIN.getRoleId());
            normalUser.setRoleID(Roles.USER.getRoleId());

            adminUser.setUsername("admin");
            adminUser.setEmail("admin@gmail.com");

            normalUser.setUsername("user");
            normalUser.setEmail("user@gmail.com");

            BCrypt bcrypt = new BCrypt();
            adminUser.setPassword(bcrypt.hashPassword("admin"));
            normalUser.setPassword(bcrypt.hashPassword("user"));

            userRepository.save(adminUser);
            userRepository.save(normalUser);

            AdCategory adCategory = new AdCategory();
            adCategory.setName("Tech");
            AdCategory adCategory1 = new AdCategory();
            adCategory1.setName("Home");
            AdCategory adCategory2 = new AdCategory();
            adCategory2.setName("Toys");
            AdCategory adCategory3 = new AdCategory();
            adCategory3.setName("Car");

            categoryRepository.save(adCategory);
            categoryRepository.save(adCategory1);
            categoryRepository.save(adCategory2);
            categoryRepository.save(adCategory3);
        }
    }
}