package io.github.yeqk97.dlykserver.config.handler;

import io.github.yeqk97.dlykserver.result.R;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public R handleException(Exception e) {
        return R.FAIL(e.getMessage());
    }

    @ExceptionHandler(value = DataAccessException.class)
    public R handleException(DataAccessException e) {
        return R.FAIL("数据库操作失败");
    }
}
