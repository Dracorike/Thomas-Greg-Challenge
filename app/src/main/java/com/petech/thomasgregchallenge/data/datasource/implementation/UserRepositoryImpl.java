package com.petech.thomasgregchallenge.data.datasource.implementation;

import com.petech.thomasgregchallenge.data.database.dao.UserDAO;
import com.petech.thomasgregchallenge.data.datasource.UserRepository;
import com.petech.thomasgregchallenge.data.entities.User;
import com.petech.thomasgregchallenge.data.entities.enums.UserType;
import com.petech.thomasgregchallenge.network.dtos.UserDTO;
import com.petech.thomasgregchallenge.network.services.TestAvatyApiService;
import com.petech.thomasgregchallenge.utils.AppUtils;

import java.time.ZoneId;
import java.util.List;

import retrofit2.Call;

public class UserRepositoryImpl implements UserRepository {
    private final TestAvatyApiService avatyApiService;
    private final UserDAO userDAO;

    public UserRepositoryImpl(UserDAO userDAO, TestAvatyApiService avatyApiService) {
        this.userDAO = userDAO;
        this.avatyApiService = avatyApiService;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userDAO.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            User currentUser = users.get(i);

            if (AppUtils.VALID_CPF_REGEX.matcher(currentUser.getDocumentNumber()).matches()) {
                currentUser.setUserType(UserType.CPF);
            }

            if (AppUtils.VALID_CNPJ_REGEX.matcher(currentUser.getDocumentNumber()).matches()) {
                currentUser.setUserType(UserType.CNPJ);
            }

            users.set(i, currentUser);
        }
        return users;
    }

    @Override
    public long createUser(User user) {
        long newId = userDAO.createUser(user);
        return newId;
    }

    @Override
    public int updateUser(User user) {
        return userDAO.updateUser(user);
    }

    @Override
    public int deleteUser(int userId) {
        int deletedId = userDAO.deleteUser(userId);
        return deletedId;
    }

    @Override
    public List<User> findUserBy(String tagColumn, String value) {
        List<User> users = userDAO.findUserBy(tagColumn, value);
        for (int i = 0; i < users.size(); i++) {
            User currentUser = users.get(i);

            if (AppUtils.VALID_CPF_REGEX.matcher(currentUser.getDocumentNumber()).matches()) {
                currentUser.setUserType(UserType.CPF);
            }

            if (AppUtils.VALID_CNPJ_REGEX.matcher(currentUser.getDocumentNumber()).matches()) {
                currentUser.setUserType(UserType.CNPJ);
            }

            users.set(i, currentUser);
        }
        return users;
    }

    @Override
    public void closeDatabase() {
        userDAO.closeDatabase();
    }

    @Override
    public Call<Void> sendUserToApi(User user, String imageBase64) {
        long birthDayTimestamp = user.getBirthDate()
                .atStartOfDay(ZoneId.of("GMT"))
                .toInstant()
                .toEpochMilli();


        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setUsername(user.getUserName());
        userDTO.setPassword(userDTO.getPassword());
        userDTO.setFoto(imageBase64);
        userDTO.setEndereco(user.getAddress());
        userDTO.setEmail(user.getEmail());
        userDTO.setDataNascimento(Long.toString(birthDayTimestamp));
        userDTO.setSexo(user.isGender());
        userDTO.setCpfCnpj(user.getDocumentNumber());
        return avatyApiService.postUser(userDTO);
    }
}
