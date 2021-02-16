package com.apsout.electronictestimony.api.serviceimpl;

import com.apsout.electronictestimony.api.entity.Stamplayoutfile;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.exception.ReportParameterNotFoundException;
import com.apsout.electronictestimony.api.entity.jasper.LayoutEntity;
import com.apsout.electronictestimony.api.repository.StamplayoutfileRepository;
import com.apsout.electronictestimony.api.service.StamplayoutfileService;
import com.apsout.electronictestimony.api.util.excel.ExcelUtil;
import com.apsout.electronictestimony.api.util.file.FileUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class StamplayoutfileServiceImpl implements StamplayoutfileService {
    private static final Logger logger = LoggerFactory.getLogger(StamplayoutfileServiceImpl.class);

    @Autowired
    private StamplayoutfileRepository repository;

    @Override
    public Stamplayoutfile save(Stamplayoutfile stamplayoutfile) {
        return repository.save(stamplayoutfile);
    }

    @Override
    public Stamplayoutfile getByWorkflowId(int workflowId) {
        Optional<Stamplayoutfile> optional = findByWorkflowId(workflowId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Optional<Stamplayoutfile> findByWorkflowId(int workflowId) {
        return repository.findByWorkflow(workflowId);
    }

    @Override
    public Stamplayoutfile getBy(Workflow workflow) {
        return this.getByWorkflowId(workflow.getId());
    }

    @Override
    public Optional<Stamplayoutfile> findBy(Workflow workflow) {
        return findByWorkflowId(workflow.getId());
    }

    @Override
    public Optional<Stamplayoutfile> findBy(int stamplayoutfileId) {
        return repository.findBy(stamplayoutfileId);
    }

    @Override
    public Stamplayoutfile getBy(int stamplayoutfileId) {
        Optional<Stamplayoutfile> optional = this.findBy(stamplayoutfileId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException(String.format("Stamplayoutfile not found with stamplayoutfileId: %d", stamplayoutfileId));
    }

    @Override
    public File buildUniqueFile(Stamplayoutfile stamplayoutfile) {
        byte[] data = stamplayoutfile.getData();
        String extension = stamplayoutfile.getExtension();
        byte[] excelData = stamplayoutfile.getExcelData();
        String excelExtension = stamplayoutfile.getExcelExtension();
        File file = FileUtil.write2NewFile(data, "temp", "." + extension);
        File excelFile = FileUtil.write2NewFile(excelData, "temp", "." + excelExtension);
        List<LayoutEntity> rows = ExcelUtil.read(excelFile);
        File target = FileUtil.createTempFile("temp", ".pdf").toFile();
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(file.getAbsolutePath(), new HashMap<>(), new JRBeanCollectionDataSource(rows));
            JasperExportManager.exportReportToPdfFile(jasperPrint, target.getAbsolutePath());
            return target;
        } catch (JRException e) {
            logger.error(String.format("Matching .jasper and .xlsx on stamplayoutId: %d", stamplayoutfile.getId()), e);
            throw new ReportParameterNotFoundException(String.format("Inconsistencia entre los parámetros de la plantilla .jrxml y el archivo de datos .xlsx para stamplayoutId: %d", stamplayoutfile.getId()), e);
        }
    }
}
