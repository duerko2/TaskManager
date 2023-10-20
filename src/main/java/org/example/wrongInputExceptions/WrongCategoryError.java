package org.example.wrongInputExceptions;

public class WrongCategoryError extends Exception {
    public WrongCategoryError(String invalid_category) {
        super(invalid_category);
    }

}
