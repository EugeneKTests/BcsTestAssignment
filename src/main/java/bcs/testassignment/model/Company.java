package bcs.testassignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Company {

    @SerializedName("sector")
    @Expose
    private String sector;
}
