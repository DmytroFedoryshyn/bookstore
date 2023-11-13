package bookstore.service;

import bookstore.model.Role;
import bookstore.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role add(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getByName(Role.RoleName roleName) {
        return roleRepository.getRoleByRoleName(roleName);
    }
}
