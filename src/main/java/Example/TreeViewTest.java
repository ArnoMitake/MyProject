package Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeViewTest {

    public static void main(String[] args) {
        SmSumOrderTimeModel modelA = new SmSumOrderTimeModel("A", "in", "20231121", 2);
        SmSumOrderTimeModel modelB = new SmSumOrderTimeModel("B", "in", "20231121", 2);
        SmSumOrderTimeModel modelC = new SmSumOrderTimeModel("A", "in", "20231122", 2);
        SmSumOrderTimeModel modelD = new SmSumOrderTimeModel("A", "out", "20231122", 2);
        SmSumOrderTimeModel modelE = new SmSumOrderTimeModel("A", "in", "20231122", 2);

        List<SmSumOrderTimeModel> models = Arrays.asList(modelA, modelB, modelC, modelD, modelE);

        List<String> groups = new ArrayList<>(Arrays.asList("type", "name", "time"));

        PanelStatDto root = new PanelStatDto();
        root.setHeader("root");

        for (SmSumOrderTimeModel model : models) {
            PanelStatDto current = root;

            for (String group : groups) {
                String value = getValueForGroup(model, group);
                current = findOrCreateChild(current, value);
            }

            current.setCount(current.getCount() + model.getSmCount());
        }

        updateCounts(root);
        printPanelStatDto(root, "");
    }

    private static String getValueForGroup(SmSumOrderTimeModel model, String group) {
        switch (group) {
            case "name":
                return model.getName();
            case "type":
                return model.getType();
            case "time":
                return model.getOrderTime();
            default:
                throw new IllegalArgumentException("Unknown group: " + group);
        }
    }

    private static PanelStatDto findOrCreateChild(PanelStatDto parent, String value) {
        for (PanelStatDto child : parent.getChildren()) {
            if (child.getHeader().equals(value)) {
                return child;
            }
        }

        PanelStatDto newChild = new PanelStatDto();
        newChild.setHeader(value);
        parent.getChildren().add(newChild);

        return newChild;
    }

    private static void updateCounts(PanelStatDto node) {
        for (PanelStatDto child : node.getChildren()) {
            updateCounts(child);
            node.setCount(node.getCount() + child.getCount());
        }
    }

    private static void printPanelStatDto(PanelStatDto node, String indent) {
        System.out.println(indent + node.getHeader() + " -> " + node.getCount());

        for (PanelStatDto child : node.getChildren()) {
            printPanelStatDto(child, indent + "\t");
        }
    }

    public static class SmSumOrderTimeModel {

        /**
         * @param name
         * @param type
         * @param orderTime
         * @param smCount
         */
        public SmSumOrderTimeModel(String name, String type, String orderTime, int smCount) {
            this.name = name;
            this.type = type;
            this.orderTime = orderTime;
            this.smCount = smCount;
        }

        private String name; // 帳號
        private String type; // 種類
        private String orderTime; // 送達時間 此欄位只顯示在 type
        private int smCount; // 發送總數

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public int getSmCount() {
            return smCount;
        }

        public void setSmCount(int smCount) {
            this.smCount = smCount;
        }
    }

    public static class PanelStatDto {
        private String header;
        private int count; // 發送總數

        private List<PanelStatDto> children = new ArrayList<>();// 子節點

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<PanelStatDto> getChildren() {
            return children;
        }

        public void setChildren(List<PanelStatDto> children) {
            this.children = children;
        }

        public void updateCount() {
            this.count = this.children.stream().mapToInt(PanelStatDto::getCount).sum();
        }

    }

}

