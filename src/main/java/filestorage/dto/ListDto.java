package filestorage.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ListDto {
    private int count;
    private List<?> items;

    public ListDto(List<?> items) {
        count = items.size();
        this.items = items;
    }
}
