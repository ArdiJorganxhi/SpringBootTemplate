package com.example.springboottemplate.service.base;

import com.example.springboottemplate.entity.base.BaseEntity;
import com.example.springboottemplate.mapper.base.BaseMapper;
import com.example.springboottemplate.model.PagingResult;
import com.example.springboottemplate.model.dto.base.BaseDto;
import com.example.springboottemplate.model.error.GenericErrorMessage;
import com.example.springboottemplate.model.exception.NotFoundException;
import com.example.springboottemplate.model.request.PaginationRequest;
import com.example.springboottemplate.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseService<E extends BaseEntity, D extends BaseDto, R extends JpaRepository<E, Long>, M extends BaseMapper<E, D>> {

    protected final R repository;
    protected final M mapper;
    protected final String serviceName = this.getClass().getSimpleName().replace("Service", "");

    public void save(E entity) {
        repository.save(entity);
    }

    public PagingResult<D> findAll(PaginationRequest request) {
        final Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        final Page<E> entities = repository.findAll(pageable);
        final List<D> entitiesDto = entities.stream().map(mapper::convertToDto).toList();
        return new PagingResult<>(
                entitiesDto,
                entities.getTotalPages(),
                entities.getTotalElements(),
                entities.getSize(),
                entities.getNumber(),
                entities.isEmpty()
        );
    }

    public D findById(Long id) {
        E entity = repository.findById(id).orElseThrow(
                () -> new NotFoundException(GenericErrorMessage
                        .builder()
                        .message(String.format("%s with id %d not found", serviceName, id))
                        .build())
        );
        return mapper.convertToDto(entity);
    }

    public void deleteById(Long id) {
        E entity = repository.findById(id).orElseThrow(
                () -> new NotFoundException(GenericErrorMessage
                        .builder()
                        .message(String.format("%s with id %d not found", serviceName, id))
                        .build())
        );;
        entity.setActive(false);
        save(entity);
    }
}
