package com.dncomponents.client.components;

/**
 * @author nikolasavic
 */
public interface CellServerResponse {
    void success(boolean b, String message);

    void revertLastValue();
}
