package common.master.model.dto.sale;

import java.time.LocalDateTime;
import java.util.List;

public class SaleHeaderList {
    public List<SaleHeader> headers;
    public static class SaleHeader {
        public int id;
        public LocalDateTime saleDttm;
        public int totalQty;
        public int totalPrice;
    }
}

