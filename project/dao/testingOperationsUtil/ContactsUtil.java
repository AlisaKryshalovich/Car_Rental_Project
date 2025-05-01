package com.project.dao.testingOperationsUtil;

import com.project.dao.impl.ContactsImplDao;
import com.project.dao.impl.UsersImplDao;
import com.project.entity.ContactsEntity;
import com.project.entity.UsersEntity;
import lombok.experimental.UtilityClass;
import java.util.*;

@UtilityClass
public class ContactsUtil {

    private static void findContactByIdTest() {
        Optional<ContactsEntity> contactId = ContactsImplDao.getInstance().findById(5L);
        System.out.println(contactId);
    }

    private static void findAllContactsTest() {
        List<ContactsEntity> allContacts = ContactsImplDao.getInstance().findAll();
        System.out.println(allContacts);
    }

    private static void saveContactTest(Long userId) {
        ContactsImplDao contactDao = ContactsImplDao.getInstance();
        UsersImplDao userDao = UsersImplDao.getInstance();

        Optional<UsersEntity> userById = userDao.findById(userId);
        if (userById.isPresent()) {
            UsersEntity userEntity = userById.get();
            System.out.println("Пользователь: First name='" + userEntity.getFirstName() + "' Last name='" + userEntity.getLastName() + "'");

            ContactsEntity contactEntity = new ContactsEntity();

            contactEntity.setUser(userEntity);
            contactEntity.setEmail("test2@gmail.com");
            contactEntity.setPhone("test2");
            ContactsEntity savedContact = contactDao.save(contactEntity);
            System.out.println(savedContact);
        }
    }

    private static void updateContactTest() {
        ContactsImplDao contactsDao = ContactsImplDao.getInstance();
        Optional<ContactsEntity> contactById = contactsDao.findById(7L);
        System.out.println(contactById);

        contactById.map(contactsEntity -> {
            contactsEntity.setEmail("XXX@gmail.com");
            contactsDao.update(contactsEntity);
            return contactsEntity;
        });
        System.out.println(contactsDao.findById(7L));
    }

    public static void deleteContactTest() {
        ContactsImplDao contactDao = ContactsImplDao.getInstance();
        boolean deleteResult = false;
        deleteResult = contactDao.delete(7L);
        System.out.println(deleteResult);
    }
}
