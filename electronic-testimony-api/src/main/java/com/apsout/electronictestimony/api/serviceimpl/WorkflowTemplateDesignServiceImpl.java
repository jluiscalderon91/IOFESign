package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.entity.model.pojo._WorkflowTemplateDesign;
import com.apsout.electronictestimony.api.service.*;
import com.apsout.electronictestimony.api.util.StamplayoutfileAllocator;
import com.apsout.electronictestimony.api.util.allocator.*;
import com.apsout.electronictestimony.api.util.file.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkflowTemplateDesignServiceImpl implements WorkflowTemplateDesignService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowTemplateDesignServiceImpl.class);

    @Autowired
    private WorkflowService workflowService;
    @Autowired
    StamptestfileService stamptestfileService;
    @Autowired
    private StamplegendService stamplegendService;
    @Autowired
    private StampimageService stampimageService;
    @Autowired
    private StampqrcodeService stampqrcodeService;
    @Autowired
    private StampdatetimeService stampdatetimeService;
    @Autowired
    private FontsizeService fontsizeService;
    @Autowired
    private FonttypeService fonttypeService;
    @Autowired
    private FontcolorService fontcolorService;
    @Autowired
    private PagestampService pagestampService;
    @Autowired
    private ContentpositionService contentpositionService;
    @Autowired
    private SieemailService sieemailService;
    @Autowired
    private StamplayoutfileService stamplayoutfileService;
    @Autowired
    private StamprubricService stamprubricService;

    @Override
    public _WorkflowTemplateDesign getBy(int workflowId) {
        Workflow workflow = workflowService.getBy(workflowId);
        Stamptestfile stamptestfile = stamptestfileService.getBy(workflow);
        List<Stamplegend> stamplegends = stamplegendService.findBy(workflow);
        List<Stampimage> stampimages = stampimageService.findBy(workflow);
        List<Stampqrcode> stampqrcodes = stampqrcodeService.findBy(workflow);
        List<Stampdatetime> stampdatetimes = stampdatetimeService.findBy(workflow);
        Stamplayoutfile stamplayoutfile = stamplayoutfileService.getBy(workflow);
        List<Stamprubric> stamprubrics = stamprubricService.findBy(workflow);
        _WorkflowTemplateDesign workflowTemplateDesign = new _WorkflowTemplateDesign();
        workflowTemplateDesign.setStamptestfile(stamptestfile);
        workflowTemplateDesign.setStamplegends(stamplegends);
        workflowTemplateDesign.setStampimages(stampimages);
        workflowTemplateDesign.setStampqrcodes(stampqrcodes);
        workflowTemplateDesign.setStampdatetimes(stampdatetimes);
        workflowTemplateDesign.setStamplayoutfile(stamplayoutfile);
        workflowTemplateDesign.setStamprubrics(stamprubrics);
        return workflowTemplateDesign;
    }

    @Override
    public _WorkflowTemplateDesign getBy4Outside(int workflowId) {
        logger.info(String.format("Getting template design info for workflowId: %d", workflowId));
        Workflow workflow = workflowService.getBy(workflowId);
        Optional<Stamptestfile> optionalStamptestfile = stamptestfileService.findBy(workflow);
        List<Stamplegend> stamplegends = stamplegendService.findBy(workflow);
        List<Stampimage> stampimages = stampimageService.findBy(workflow);
        List<Stampqrcode> stampqrcodes = stampqrcodeService.findBy(workflow);
        Optional<Stamplayoutfile> optionalStamplayoutfile = stamplayoutfileService.findBy(workflow);
        Optional<Sieemail> optionalSieemail = sieemailService.findByWorkflow(workflow);
        _WorkflowTemplateDesign workflowTemplateDesign = new _WorkflowTemplateDesign();
        Stamptestfile stamptestfile;
        if (optionalStamptestfile.isPresent()) {
            stamptestfile = StamptestfileAllocator.forReturn(optionalStamptestfile.get());
        } else {
            Path template = FileUtil.copyInputStreamOfResources("document-test-4-sign.pdf");
            stamptestfile = StamptestfileAllocator.forReturn(template);
        }
        workflowTemplateDesign.setStamptestfile(stamptestfile);
        if (!stamplegends.isEmpty()) {
            List<Stamplegend> stamplegends1 = setLegendValues(stamplegends);
            workflowTemplateDesign.setStamplegends(stamplegends1);
        }
        if (!stampimages.isEmpty()) {
            List<Stampimage> stampimages1 = setImageValues(stampimages);
            workflowTemplateDesign.setStampimages(stampimages1);
        }
        if (!stampqrcodes.isEmpty()) {
            List<Stampqrcode> stampqrcodes1 = setQRCodeValues(stampqrcodes);
            workflowTemplateDesign.setStampqrcodes(stampqrcodes1);
        }
        if (optionalSieemail.isPresent()) {
            final Sieemail sieemail = optionalSieemail.get();
            final Sieemail sieemail4return = SieemailAllocator.forReturn(sieemail);
            workflowTemplateDesign.setSieemail(sieemail4return);
        }
        if (optionalStamplayoutfile.isPresent()) {
            Stamplayoutfile stamplayoutfile = StamplayoutfileAllocator.forReturn(optionalStamplayoutfile.get());
            workflowTemplateDesign.setStamplayoutfile(stamplayoutfile);
        }
        logger.info(String.format("Returning template design info for workflowId: %d", workflowId));
        return workflowTemplateDesign;
    }

    private List<Stamplegend> setLegendValues(List<Stamplegend> stamplegends) {
        return stamplegends.stream().map(stamplegend -> {
            Fontsize fontsize = fontsizeService.getBy(stamplegend.getFontSize());
            Fonttype fonttype = fonttypeService.getBy(stamplegend.getFontType());
            Fontcolor fontcolor = fontcolorService.getBy(stamplegend.getFontColor());
            Pagestamp pagestamp = pagestampService.getBy(stamplegend.getPageStamp());
            return StamplegendAllocator.forReturn(stamplegend, fontsize, fonttype, fontcolor, pagestamp);
        }).collect(Collectors.toList());
    }

    private List<Stampimage> setImageValues(List<Stampimage> stampimages) {
        return stampimages.stream().map(stampimage -> {
            Contentposition contentposition = contentpositionService.getBy(stampimage.getContentPosition());
            Pagestamp pagestamp = pagestampService.getBy(stampimage.getPageStamp());
            return StampimageAllocator.forReturn(stampimage, contentposition, pagestamp);
        }).collect(Collectors.toList());
    }

    private List<Stampqrcode> setQRCodeValues(List<Stampqrcode> stampqrcodes) {
        return stampqrcodes.stream().map(stampqrcode -> {
            Pagestamp pagestamp = pagestampService.getBy(stampqrcode.getPageStamp());
            return StampqrcodeAllocator.forReturn(stampqrcode, pagestamp);
        }).collect(Collectors.toList());
    }
}
