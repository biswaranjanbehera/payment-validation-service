package com.cpt.payments.service.impl;

import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.exceptions.ValidationException;
import com.cpt.payments.utils.HmacSha256Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HmacSha256ProviderImplTest {
    @InjectMocks
    HmacSha256ProviderImpl hmacSha256Provider;

    @Mock
    HmacSha256Utils hmacSha256Utils;

    @Test
    public void testNullSignature(){
        //data defining

        String requestDataAsJson = null;
        String requestSignature = null;
        //invocation
        ValidationException returnedException = assertThrows(ValidationException.class,
                ()->hmacSha256Provider.isSigValid(requestDataAsJson,requestSignature));

        //verification
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,returnedException.getHttpStatus());
        Assertions.assertEquals(ErrorCodeEnum.SIGNATURE_EMPTY.getErrorCode(),
                returnedException.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.SIGNATURE_EMPTY.getErrorMessage(),
                returnedException.getErrorMessage());

    }

    @Test
    public void testBlankSignature(){
        //data defining

        String requestDataAsJson = null;
        String requestSignature = "      ";
        //invocation
        ValidationException returnedException = assertThrows(ValidationException.class,
                ()->hmacSha256Provider.isSigValid(requestDataAsJson,requestSignature));

        //verification
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,returnedException.getHttpStatus());
        Assertions.assertEquals(ErrorCodeEnum.SIGNATURE_EMPTY.getErrorCode(),
                returnedException.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.SIGNATURE_EMPTY.getErrorMessage(),
                returnedException.getErrorMessage());

    }

    @Test
    public void testEmptySignature(){
        //data defining

        String requestDataAsJson = null;
        String requestSignature = "";
        //invocation
        ValidationException returnedException = assertThrows(ValidationException.class,
                ()->hmacSha256Provider.isSigValid(requestDataAsJson,requestSignature));

        //verification
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,returnedException.getHttpStatus());
        Assertions.assertEquals(ErrorCodeEnum.SIGNATURE_EMPTY.getErrorCode(),
                returnedException.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.SIGNATURE_EMPTY.getErrorMessage(),
                returnedException.getErrorMessage());

    }

    @Test
    public void testInValidSignature(){
        //data defining

        String requestDataAsJson = null;
        String requestSignature = "input-test-signature";

        //BY DEFAULT hmacSha256Utils.verify will return false when we mock it.
        //so no need to write the below linw that's why comment it.
        //when(hmacSha256Utils.verify(any(),any(),any())).thenReturn(false);


        //invocation
        ValidationException returnedException = assertThrows(ValidationException.class,
                ()->hmacSha256Provider.isSigValid(requestDataAsJson,requestSignature));

        //verification
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,returnedException.getHttpStatus());
        Assertions.assertEquals(ErrorCodeEnum.SIGNATURE_INVALID.getErrorCode(),
                returnedException.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.SIGNATURE_INVALID.getErrorMessage(),
                returnedException.getErrorMessage());

    }

    @Test
    public void testValidSignature(){
        //data defining

        String requestDataAsJson = null;
        String requestSignature = "input-test-signature";

        when(hmacSha256Utils.verify(any(),any(),any())).thenReturn(true);
        //invocation
        boolean isSigValid = Assertions.assertDoesNotThrow
                (()->hmacSha256Provider.isSigValid(requestDataAsJson,requestSignature));

        //verification
        Assertions.assertTrue(isSigValid);

    }
}
