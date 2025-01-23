package com.doublevpartners.tutickets.util;

public class Constants {

  public static final int MAX_CHARACTER = 50;
  public static final String INCLINED_BAR = "/";
  public static final String DOT = ".";
  public static final String API_VERSION = "v1";
  public static final String API = "api";
  public static final String PATH_VARIABLE_ID = INCLINED_BAR + "{id}";
  public static final String PATH_USERS = INCLINED_BAR + API_VERSION + INCLINED_BAR + API + INCLINED_BAR + "users";
  public static final String PATH_USER = INCLINED_BAR + "user";
  public static final String PATH_ESTATUS = INCLINED_BAR + "estatus";
  public static final String PATH_VARIABLE_ESTATUS = INCLINED_BAR + "{estatus}";
  public static final String PATH_VARIABLE_USER_ID = INCLINED_BAR + "{userId}";
  public static final String PATH_TICKETS = INCLINED_BAR + API_VERSION + INCLINED_BAR + API + INCLINED_BAR + "tickets";
  public static final String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
  public static final String NAME_IS_REQUIRED = "name is required";
  public static final String NAME_TOO_LARGE = "name must not exceed " + MAX_CHARACTER + " characters";
  public static final String LASTNAME_IS_REQUIRED = "lastname is required";
  public static final String LASTNAME_TOO_LARGE = "lastname must not exceed " + MAX_CHARACTER + " characters";
  public static final String USER_NOT_FOUND_WITH_ID = "User not found with id: ";
  public static final String USER_CREATED_SUCCESSFULLY = "User created successfully.";
  public static final String USER_UPDATED_SUCCESSFULLY = "User updated successfully.";
  public static final String TICKET_CREATED_SUCCESSFULLY = "Ticket created successfully.";
  public static final String TICKET_UPDATED_SUCCESSFULLY = "Ticket updated successfully.";
  public static final String TICKET_DELETED_SUCCESSFULLY = "Ticket deleted successfully.";
  public static final String TICKED_NOT_FOUND_WITH_ID = "Ticket not found with id: ";
  public static final String MESSAGE = "message";

  private Constants() {
  }
}
