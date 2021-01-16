package com.pd.testCredit.testObjects;

import java.util.UUID;

public class UUIDSFactory {

    private static String ID_STRING_1 ="JUST_A_TEST_STRING";
    private static String ID_STRING_2 ="JUST_ANOTHER_TEST_STRING";

    public static UUID uuid1 = UUID.nameUUIDFromBytes(ID_STRING_1.getBytes());
    public static UUID uuid2 = UUID.nameUUIDFromBytes(ID_STRING_2.getBytes());

}
