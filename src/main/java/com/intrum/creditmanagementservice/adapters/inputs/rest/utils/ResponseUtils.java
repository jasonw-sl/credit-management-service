package com.intrum.creditmanagementservice.adapters.inputs.rest.utils;

import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.BasicResponse;
import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Function;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.SOMETHING_WENT_WRONG;

@Log4j2
public final class ResponseUtils {

    @SuppressWarnings("unchecked")
    public static <T, R extends BasicResponse> ResponseEntity<R> handle(T t, Function<T, R> function, HttpStatus status) {
        String uuid = UUID.randomUUID().toString();
        LocalDateTime timestamp = LocalDateTime.now();

        try {
            R result = function.apply(t);
            result.setTimestamp(timestamp);
            return new ResponseEntity<>(result, status);
        } catch (CoreException e) {
            log.error(uuid);
            log.error(e);
            e.printStackTrace();
            R r = (R) new BasicResponse()
                    .setCode(e.getErrorMessage().getCode())
                    .setMessage(e.getErrorMessage().getText())
                    .setTimestamp(timestamp)
                    .setObject(e.getObject())
                    .setUuid(uuid);
            if (Status.VALIDATION_FAIL.equals(e.getStatus())) {
                return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
            }
            if (Status.CONFLICT.equals(e.getStatus())) {
                return new ResponseEntity<>(r, HttpStatus.CONFLICT);
            }
            if (Status.NO_DATA.equals(e.getStatus())) {
                return new ResponseEntity<>(r, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(uuid);
            e.printStackTrace();
            return new ResponseEntity<>(
                    (R) new BasicResponse()
                            .setCode(SOMETHING_WENT_WRONG.getCode())
                            .setMessage(SOMETHING_WENT_WRONG.getText())
                            .setTimestamp(timestamp)
                            .setObject("Please contact with Intrum service desk!")
                            .setUuid(uuid)
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>((R) new BasicResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseUtils() throws IllegalAccessException {
        throw new IllegalAccessException();
    }
}

