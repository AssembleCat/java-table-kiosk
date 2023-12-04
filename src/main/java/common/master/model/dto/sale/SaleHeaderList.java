package common.master.model.dto.sale;

import java.util.List;

public class SaleHeaderList {
    public List<SaleHeader> headers;

    public static class SaleHeader {
        public int id;
        public String saleDttm;
        public int totalQty;
        public int totalPrice;
    }
}

