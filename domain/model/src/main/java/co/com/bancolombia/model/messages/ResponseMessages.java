package co.com.bancolombia.model.messages;

import lombok.Getter;

@Getter
public enum ResponseMessages {

    FRANCHISE_SAVED("200", "Franchise successfully saved."),
    ERROR_SAVE_FRANCHISE("500", "An error occurred while saving the franchise."),

    BRANCH_SAVED("200", "Branch successfully saved."),
    ERROR_ADD_BRANCH_TO_FRANCHISE("500", "An error occurred while adding the branch to the franchise."),
    ERROR_VALIDATE_STOCK("500", "Error, the new stock  must be greater than or equal to zero."),

    STOCK_CHANGED("200", "Product stock successfully updated."),
    GET_STOCK("200", "The products have been successfully listed."),

    PRODUCT_SAVED("200", "Product successfully saved."),
    PRODUCT_DELETED("200", "Product successfully deleted."),
    ERROR_SAVE_PRODUCT("500", "An error occurred while saving the product."),
    ERROR_DELETE_PRODUCT("500", "An error occurred while deleting the product."),

    ERROR_CHANGE_STOCK("500", "An error occurred while updating the product stock."),
    ERROR_GET_HIGHEST_STOCK("500", "An error occurred while get the highest stock level."),
    ERROR_STOCK_VALIDATION("400", "Invalid stock value."),

    INTERNAL_SERVER_ERROR("500", "Internal server error.");


    private final String statusCode;
    private final String description;

    ResponseMessages(String statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }
}
