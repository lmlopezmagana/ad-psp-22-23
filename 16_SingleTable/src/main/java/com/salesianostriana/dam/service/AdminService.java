package com.salesianostriana.dam.service;

import com.salesianostriana.dam.model.Admin;
import com.salesianostriana.dam.repos.AdminRepository;
import com.salesianostriana.dam.service.base.BaseService;
import com.salesianostriana.dam.service.base.BaseUsuarioService;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends BaseUsuarioService<Admin, AdminRepository> {
}
