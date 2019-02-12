package com.test.celebrity.exception;

/**
 * CelebrityTestApplication
 *
 * @author christian.valencia
 * @since 11/02/2019
 */
public class CelebrityFinderException extends RuntimeException {
    /**
     *
     */
    public CelebrityFinderException() {
        super();
    }

    /**
     *
     * @param s
     */
    public CelebrityFinderException(String s) {
        super(s);
    }

    /**
     *
     * @param s
     * @param throwable
     */
    public CelebrityFinderException(String s, Throwable throwable) {
        super(s, throwable);
    }

    /**
     *
     * @param throwable
     */
    public CelebrityFinderException(Throwable throwable) {
        super(throwable);
    }
}
