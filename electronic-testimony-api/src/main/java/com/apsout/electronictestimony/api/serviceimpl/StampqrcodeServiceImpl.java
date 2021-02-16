package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Stampqrcode;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.repository.StampqrcodeRepository;
import com.apsout.electronictestimony.api.service.StampqrcodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StampqrcodeServiceImpl implements StampqrcodeService {
    private static final Logger logger = LoggerFactory.getLogger(StampqrcodeServiceImpl.class);

    @Autowired
    private StampqrcodeRepository repository;

    @Override
    public Stampqrcode save(Stampqrcode stampqrcode) {
        return repository.save(stampqrcode);
    }

    @Override
    public List<Stampqrcode> save(List<Stampqrcode> stampqrcodes) {
        return stampqrcodes.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public List<Stampqrcode> findByWorkflowId(int workflowId) {
        return repository.findAllByWorkflow(workflowId);
    }

    @Override
    public List<Stampqrcode> findBy(Workflow workflow) {
        return this.findByWorkflowId(workflow.getId());
    }

    @Override
    public Optional<Stampqrcode> findBy(int stampqrcodeId) {
        return repository.findBy(stampqrcodeId);
    }

    @Override
    public Stampqrcode getBy(int stampqrcodeId) {
        Optional<Stampqrcode> optional = this.findBy(stampqrcodeId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Stampqrcode not found with stampqrcodeId: %d", stampqrcodeId));
    }


}
