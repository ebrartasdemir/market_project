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
        public static final String CANT_BE_EMPTY = "Product can't be empty.";
        public static final String RECORDS_FOUND_AND_LISTED = "Product records listed successfully.";
        public static final String RECORD_NOT_FOUND_AND_LISTED_ERROR = "Product records cannot be listed, something went wrong.";
        public static final String RECORD_FOUND = "Product found successfully. id: %d.";
        public static final String RECORD_OUT_OF_STOCK="Product stock isn't enough.";
        public static final String RECORD_NOT_FOUND = "Products cannot be found. id: %d";
        public static final String RECORD_NOT_FOUND_BY_ORDER_CODE = "Products cannot be found. order code: %s";
        public static final String RECORD_NOT_FOUND_ERROR = "Products cannot be listed. Something went wrong.";
        public static final String RECORDS_FOUND_AND_LISTED_WITH_CATEGORY = "Products belongs to category listed successfully. Category id: %d.";
        public static final String RECORDS_FOUND_AND_LISTED_WITH_NAME = "Products belongs with this name listed successfully. Name: %s.";
        public static final String RECORD_CREATED = "Product record created successfully.";
        public static final String RECORD_CREATED_ERROR = "Product record cannot be created. Something went wrong.";
        public static final String RECORD_UPDATED = "Product record updated successfully. id: %d.";
        public static final String RECORD_UPDATED_ERROR = "Product record cannot be updated. Something went wrong.";
        public static final String RECORD_DELETED_ERROR = "Product record cannot be deleted successfully. id: %d.";
        public static final String ID_CANT_BE_EMPTY = "Product id cannot be empty.";
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
        public static final String  RECORD_NOT_FOUND="User cannot be found. id: %d";
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
    //CART
    public static class Cart {
        public static final String ID_CANT_BE_EMPTY = "Cart id cannot be empty.";
        public static final String RECORD_NOT_FOUND="Cart cannot be found. id: %d";
        public static final String RECORD_ALREADY_EXIST = "Cart record already exist for this user. userId: %s.";
        public static final String RECORD_FOUND_BY_USER_ID = "Cart  found successfully. user id: %d.";
        public static final String RECORD_NOT_FOUND_BY_USER_ID = "Cart record not found by user id: %d.";
        public static final String RECORDS_FOUND_AND_LISTED = "Cart  records listed successfully.";
        public static final String RECORD_NOT_FOUND_AND_LISTED_ERROR = "Cart  records cannot be listed, something went wrong.";
        public static final String RECORD_FOUND = "Cart  found successfully. id: %d.";
        public static final String RECORD_NOT_FOUND_ERROR = "Cart  cannot be listed. Something went wrong.";
        public static final String RECORD_CREATED = "Cart  record created successfully.";
        public static final String RECORD_CREATED_ERROR = "Cart  record cannot be created. Something went wrong.";
        public static final String RECORD_UPDATED = "Cart  record updated successfully. id: %d.";
        public static final String RECORD_UPDATE_STATUS="Cart  record status updated to %s. id: %d";
        public static final String RECORD_UPDATED_ERROR = "Cart  record cannot be updated. Something went wrong.";
        public static final String RECORD_DELETED_ERROR = "Cart  record cannot be deleted successfully. id: %d.";
        public static final String DECREASE_QUANTITY="Cart  quantity has been decreased successfully. id: %d";
        public static final String INCREASE_QUANTITY="Cart quantity has been decreased successfully. id: %d";
        //ITEM
        public static class Item {
            public static final String ID_CANT_BE_EMPTY = "Item id cannot be empty.";
            public static final String RECORD_NOT_FOUND="Cart Item cannot be found. id: %d";
            public static final String RECORDS_FOUND_AND_LISTED = "Cart Item records listed successfully.";
            public static final String RECORD_NOT_FOUND_AND_LISTED_ERROR = "Cart Item records cannot be listed, something went wrong.";
            public static final String RECORD_FOUND = "Cart Item found successfully. id: %d.";
            public static final String RECORD_NOT_FOUND_ERROR = "Cart Item cannot be listed. Something went wrong.";
            public static final String RECORD_CREATED = "Cart Item record created successfully.";
            public static final String RECORD_CREATED_ERROR = "Cart Item record cannot be created. Something went wrong.";
            public static final String RECORD_UPDATED = "Cart Item record updated successfully. id: %d.";
            public static final String RECORD_UPDATE_STATUS="Cart Item record status updated to %s. id: %d";
            public static final String RECORD_UPDATED_ERROR = "Cart Item record cannot be updated. Something went wrong.";
            public static final String RECORD_DELETED_ERROR = "Cart Item record cannot be deleted successfully. id: %d.";
            public static final String DECREASE_QUANTITY="Cart Item's quantity has been decreased successfully. id: %d";
            public static final String INCREASE_QUANTITY="Cart Item's quantity has been decreased successfully. id: %d";

        }
    }
    //ADRESS
    public static class Adress{
        public static final String  RECORD_NOT_FOUND = "Adress cannot be found. id: %d";
        public static final String  RECORD_NOT_FOUND_TITLE = "Adress cannot be found. title: %d";
        public static final String RECORD_ALREADY_EXIST = "Adress already exist. id: %d";
        public static final String  RECORD_FOUND_AND_LISTED="Adress records listed successfully.";
        public static final String RECORD_NOT_FOUND_AND_LISTED_ERROR="Adress records cannot be listed, something went wrong.";
        public static final String RECORD_CREATED = "Adress record created successfully.";
        public static final String RECORD_CREATED_ERROR = "Adress record cannot be created. Something went wrong.";
        public static  final String RECORD_UPDATED = "Adress record updated successfully.id: %d";
        public static final String RECORD_UPDATED_ERROR = "Adress record cannot be updated. Something went wrong.";
        public static final String RECORD_DELETED="Adress record deleted successfully. id: %d";
        public static final String RECORD_DELETED_ERROR = "Adress record cannot be deleted successfully.";

    }
    //ORDER
    public static class Order{
        public static final String  ID_CANT_BE_EMPTY = "Order id cannot be empty.";
        public static final String RECORD_NOT_FOUND="Order cannot be found. id: %d";
        public static final String RECORDS_FOUND_AND_LISTED = "Order records listed successfully.";
        public static final String RECORD_NOT_FOUND_AND_LISTED_ERROR = "Order records cannot be listed, something went wrong.";
        public static final String RECORD_FOUND = "Order found successfully. id: %d.";
        public static final String RECORD_FOUND_BY_ORDER_CODE = "Order found successfully. Order Code: %s.";
        public static final String RECORD_NOT_FOUND_BY_ORDER_CODE="Order cannot be found. Order Code: %s";
        public static final String RECORD_NOT_FOUND_ERROR = "Order cannot be listed. Something went wrong.";
        public static final String RECORD_CREATED = "Order record created successfully.";
        public static final String RECORD_CREATED_ERROR = "Order record cannot be created. Something went wrong.";
        public static final String RECORD_UPDATED = "Order record updated successfully. id: %d.";
        public static final String RECORD_UPDATE_STATUS="Order record status updated to %s. id: %d";
        public static final String RECORD_UPDATED_ERROR = "Order record cannot be updated. Something went wrong.";
        public static final String RECORD_DELETED_ERROR = "Order record cannot be deleted successfully. id: %d.";
        public static class  Items{
            public static final String  ID_CANT_BE_EMPTY = "Items id cannot be empty.";
            public static final String RECORD_NOT_FOUND="Order Item cannot be found. id: %d";
            public static final String RECORD_PRICE_CANT_BE_EMPTY = "Items price cannot be empty.";
            public static final String RECORDS_FOUND_AND_LISTED = "Order Item records listed successfully.";
            public static final String RECORD_NOT_FOUND_AND_LISTED_ERROR = "Order Item records cannot be listed, something went wrong.";
            public static final String RECORD_FOUND = "Order Item found successfully. id: %d.";
            public static final String RECORD_FOUND_BY_ORDER_CODE = "Order Item found successfully. Order Code: %s.";
            public static final String RECORD_NOT_FOUND_BY_ORDER_CODE="Order Items cannot be found. Order Code: %s";
            public static final String RECORD_NOT_FOUND_ERROR = "Order Item cannot be listed. Something went wrong.";
            public static final String RECORD_CREATED = "Order Item record created successfully.";
            public static final String RECORD_CREATED_ERROR = "Order Item record cannot be created. Something went wrong.";
            public static final String RECORD_UPDATED = "Order Item record updated successfully. id: %d.";
            public static final String RECORD_UPDATE_STATUS="Order Item record status updated to %s. id: %d";
            public static final String RECORD_UPDATED_ERROR = "Order Item record cannot be updated. Something went wrong.";
            public static final String RECORD_DELETED_ERROR = "Order Item record cannot be deleted successfully. id: %d.";

        }
    }
}
