package com.apsout.electronictestimony.api.modelassembler;

import com.apsout.electronictestimony.api.controller.ResourceController;
import com.apsout.electronictestimony.api.entity.Resource;
import com.apsout.electronictestimony.api.entity.model.ResourceModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ResourceModelAssembler extends RepresentationModelAssemblerSupport<Resource, ResourceModel> {

    public ResourceModelAssembler() {
        super(ResourceController.class, ResourceModel.class);
    }

    @Override
    public ResourceModel toModel(Resource resource) {
        ResourceModel documentModel = instantiateModel(resource);
        documentModel.add(linkTo(methodOn(ResourceController.class).getBy(resource.getId(), null)).withSelfRel());
        documentModel.add(linkTo(methodOn(ResourceController.class).getStreamBy(resource.getId(), null)).withRel("stream"));
        documentModel.setId(resource.getId());
        documentModel.setType(resource.getType());
        documentModel.setPath(resource.getPath());
        documentModel.setOriginalName(resource.getOriginalName());
        documentModel.setNewName(resource.getNewName());
        documentModel.setExtension(resource.getExtension());
        documentModel.setLength(resource.getLength());
        documentModel.setCreateAt(resource.getCreateAt());
        documentModel.setActive(resource.getActive());
        documentModel.setDeleted(resource.getDeleted());
        documentModel.setObservation(resource.getObservation());
        return documentModel;
    }
}
