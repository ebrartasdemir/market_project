package com.market_p.market_p.example.constants;

public class Messages {
    public static final String EMPTY_BODY="Body is empty!";
    //_____________________________________________________API_____________________________________________________//
    //CATEGORIES
    public static class Category {
        public static final String RECORDS_FOUND_AND_LISTED = "All category records listed successfully.";
        public static final String RECORD_NOT_FOUND_AND_LISTED_ERROR = "Category records cannot be listed, something went wrong.";
        public static final String RECORD_FOUND = "Category record found successfully. id: %d.";
        public static final String RECORD_NOT_FOUND = "Category record not found. id: %d.";
        public static final String RECORD_NOT_FOUND_ERROR = "Category record cannot be found. Something went wrong.";
        public static final String RECORD_CREATED = "Category record created successfully.";
        public static final String RECORD_CREATED_ERROR = "Category record cannot be created. Something went wrong.";
        public static final String RECORD_UPDATED = "Category record updated successfully. id: %d.";
        public static final String RECORD_UPDATED_ERROR = "Category cannot be updated. Something went wrong.";
        public static final String RECORD_DELETED_ERROR = "Category cannot be deleted. Something went wrong.";
        public static final String INVALID_BLANK_NAME ="Category name cannot be empty.";
    }
    //PRODUCTS
    public static class Product {
        public static final String RECORDS_FOUND_AND_LISTED = "Product records listed successfully.";
        public static final String RECORD_NOT_FOUND_AND_LISTED_ERROR = "Product records cannot be listed, something went wrong.";
        public static final String RECORD_FOUND = "Product found successfully. id: %d.";
        public static final String RECORD_NOT_FOUND = "Products cannot be found. id: %d";
        public static final String RECORD_NOT_FOUND_ERROR = "Products cannot be listed. Something went wrong.";
        public static final String RECORDS_FOUND_AND_LISTED_WITH_CATEGORY = "Products belongs to category listed successfully. Category id: %d.";
        public static final String RECORDS_FOUND_AND_LISTED_WITH_NAME = "Products belongs with this name listed successfully. Name: %s.";
        public static final String RECORD_CREATED = "Product record created successfully.";
        public static final String RECORD_CREATED_ERROR = "Product record cannot be created. Something went wrong.";
        public static final String RECORD_UPDATED = "Product record updated successfully. id: %d.";
        public static final String RECORD_UPDATED_ERROR = "Product record cannot be updated. Something went wrong.";
        public static final String RECORD_DELETED_ERROR = "Product record cannot be deleted successfully. id: %d.";
        public static final String INVALID_BLANK_NAME ="Product name cannot be empty.";
        public static final String INVALID_MAX_CHARACTERS_LENGTH_NAME="Product name cannot be longer than 25 characters.";
        public static final String INVALID_MAX_CHARACTERS_LENGTH_DESCRIPTION="Product description cannot be longer than 1000 characters";
        public static final String INVALID_PRICE_CANT_BE_NEGATIVE="Product price cannot be negative.";
        public static final String INVALID_QUANTITY_CANT_BE_NEGATIVE="Product quantity cannot be negative.";

    }
    //USER
    public static class User{
        public static final String  NAME_CANT_BE_EMPTY = "Name cannot be empty.";
        public static final String  NAME_MUST_OBEY_REGEX_RULES="Name can only contain alphabetic characters.";
        public static final String  PASSWORD_CANT_BE_EMPTY = "Password cannot be empty.";
        public static final String  PASSWORD_MUST_OBEY_REGEX_RULES="Password must be between 10-25 characters.";
        public static final String  EMAIL_CANT_BE_EMPTY = "Email cannot be empty.";
        public static final String  EMAIL_MUST_OBEY_REGEX_RULES="Email must be in a form like 'test@test.com'.";
        public static final String  PHONE_CANT_BE_EMPTY = "Phone cannot be empty.";
        public static final String  PHONE_MUST_OBEY_REGEX_RULES="Phone Number must contains only 10 digits.";
        public static final String  SURNAME_CANT_BE_EMPTY = "Surname cannot be empty.";
        public static final String  SURNAME_MUST_OBEY_REGEX_RULES="Surname can only contain alphabetic characters.";
        public static final String RECORD_NOT_FOUND_BY_EMAIL = "User not found by email: %s.";
        public static final String ALREADY_REGISTERED_EMAIL = "This email: %s already exists.";
        public static final String RECORD_CREATED_SUCCESSFULLY = "User created successfully.";
        public static final String REGISTER_FAILED = "Cannot register user, something went wrong";
        public static final String LOGIN_FAILED = "Cannot login user, something went wrong";
    }
    //ROLE
    public static class Role{
        public static final String RECORD_NOT_FOUND_BY_TITLE = "Role not found by title: %s.";
    }
    //AUTH
    public static class Auth{
        public static final String INVALID_TOKEN="Token has been expaired or uncorrect.";
        public static final String VALID_TOKEN="Token is valid.";
    }
}
