package com.contactapp.service;

import com.contactapp.dto.ContactDTO;
import com.contactapp.entity.Contact;
import com.contactapp.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public ContactDTO createContact(ContactDTO contactDTO) {
        log.info("Creating new contact: {}", contactDTO.getName());

        Contact contact = new Contact();
        contact.setName(contactDTO.getName());
        contact.setAddress(contactDTO.getAddress());
        contact.setPhoneNumber(contactDTO.getPhoneNumber());

        Contact savedContact = contactRepository.save(contact);
        return convertToDTO(savedContact);
    }

    public List<ContactDTO> getAllContacts() {
        log.info("Fetching all contacts");
        return contactRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ContactDTO getContactById(Long id) {
        log.info("Fetching contact with id: {}", id);
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));
        return convertToDTO(contact);
    }

    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        log.info("Updating contact with id: {}", id);

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));

        contact.setName(contactDTO.getName());
        contact.setAddress(contactDTO.getAddress());
        contact.setPhoneNumber(contactDTO.getPhoneNumber());

        Contact updatedContact = contactRepository.save(contact);
        return convertToDTO(updatedContact);
    }

    public void deleteContact(Long id) {
        log.info("Deleting contact with id: {}", id);

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));

        contactRepository.delete(contact);
    }

    private ContactDTO convertToDTO(Contact contact) {
        return new ContactDTO(
                contact.getId(),
                contact.getName(),
                contact.getAddress(),
                contact.getPhoneNumber(),
                contact.getCreatedAt()
        );
    }
}
