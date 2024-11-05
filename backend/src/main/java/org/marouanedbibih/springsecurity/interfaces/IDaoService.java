package org.marouanedbibih.springsecurity.interfaces;

import java.util.List;

import org.marouanedbibih.springsecurity.exception.AlreadyExistException;
import org.marouanedbibih.springsecurity.exception.MyNotDeleteException;
import org.marouanedbibih.springsecurity.exception.MyNotFoundException;
import org.marouanedbibih.springsecurity.exception.MyNotSave;

public interface IDaoService<E, DTO, CREQ, UREQ, ID> {
    DTO create(CREQ req) throws MyNotSave, AlreadyExistException;

    DTO update(UREQ req,ID id) throws MyNotSave, AlreadyExistException;

    List<DTO> getAll();

    DTO get(ID id) throws MyNotFoundException;

    void delete(ID id) throws MyNotFoundException, MyNotDeleteException;
}
