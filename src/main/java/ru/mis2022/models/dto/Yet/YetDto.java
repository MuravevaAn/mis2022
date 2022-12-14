package ru.mis2022.models.dto.Yet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;
import lombok.Builder;
import ru.mis2022.utils.validation.OnCreate;
import ru.mis2022.utils.validation.OnUpdate;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.time.YearMonth;


@Builder
public record YetDto(

        @Null(groups = OnCreate.class, message = "id должен быть равен null")
        @Positive(groups = OnUpdate.class, message = "id должен быть положительным")
        Long id,

        @Positive(message = "Цена должна быть положительной")
        Double price,

        @JsonSerialize(using = YearMonthSerializer.class)
        @JsonDeserialize(using = YearMonthDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM.yyyy")
        YearMonth dayFrom,

        @JsonSerialize(using = YearMonthSerializer.class)
        @JsonDeserialize(using = YearMonthDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM.yyyy")
        YearMonth dayTo) {

}
