package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Job;
import com.apsout.electronictestimony.api.entity.Observationcancel;
import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.model.pojo._Operator;
import com.apsout.electronictestimony.api.util.date.DateUtil;
import com.apsout.electronictestimony.api.util.statics.States;

public class _OperatorAllocator {

    public static _Operator build(Person person,
                                  Observationcancel observationcancel,
                                  Job job) {
        _Operator _operator = new _Operator();
        _operator.setComment(observationcancel.getDescription());
        _operator.setOperationDescription("-");
        _operator.setOperatorName(person.getFullname());
        _operator.setJobDescription(job.getDescription());
        _operator.setStateDescription("Anulado");
        _operator.setOperatedAt(DateUtil.build(observationcancel.getCreateAt(), "dd-MM-yyyy"));
        _operator.setIsOperator(States.IS_NOT_OPERATOR);
        return _operator;
    }
}
