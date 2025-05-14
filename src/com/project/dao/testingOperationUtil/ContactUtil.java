//package com.project.dao.testingOperationUtil;
//
//import com.project.dao.impl.ContactImplDao;
//import com.project.dao.impl.UserImplDao;
//import com.project.entity.ContactEntity;
//import com.project.entity.UserEntity;
//import lombok.experimental.UtilityClass;
//import java.util.*;
//
//@UtilityClass
//public class ContactUtil {
//
//    private static void findContactByIdTest() {
//        Optional<ContactEntity> contactId = ContactImplDao.getInstance().findById(5L);
//        System.out.println(contactId);
//    }
//
//    private static void findAllContactsTest() {
//        List<ContactEntity> allContacts = ContactImplDao.getInstance().findAll();
//        System.out.println(allContacts);
//    }
//
//    private static void saveContactTest(Long userId) {
//        ContactImplDao contactDao = ContactImplDao.getInstance();
//        UserImplDao userDao = UserImplDao.getInstance();
//
//        Optional<UserEntity> userById = userDao.findById(userId);
//        if (userById.isPresent()) {
//            UserEntity userEntity = userById.get();
//            System.out.println("Пользователь: First name='" + userEntity.getFirstName() + "' Last name='" + userEntity.getLastName() + "'");
//
//            ContactEntity contactEntity = new ContactEntity();
//
//            contactEntity.setUser(userEntity);
//            contactEntity.setEmail("test2@gmail.com");
//            contactEntity.setPhone("test2");
//            ContactEntity savedContact = contactDao.save(contactEntity);
//            System.out.println(savedContact);
//        }
//    }
//
//    private static void updateContactTest() {
//        ContactImplDao contactsDao = ContactImplDao.getInstance();
//        Optional<ContactEntity> contactById = contactsDao.findById(7L);
//        System.out.println(contactById);
//
//        contactById.map(contactEntity -> {
//            contactEntity.setEmail("XXX@gmail.com");
//            contactsDao.update(contactEntity);
//            return contactEntity;
//        });
//        System.out.println(contactsDao.findById(7L));
//    }
//
//    public static void deleteContactTest() {
//        ContactImplDao contactDao = ContactImplDao.getInstance();
//        boolean deleteResult = false;
//        deleteResult = contactDao.delete(7L);
//        System.out.println(deleteResult);
//    }
//}
