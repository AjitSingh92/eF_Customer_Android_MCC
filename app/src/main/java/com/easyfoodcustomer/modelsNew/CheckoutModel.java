package com.easyfoodcustomer.modelsNew;

import java.io.Serializable;
import java.util.List;

public class CheckoutModel implements Serializable {


    /**
     * id : 0
     * menuCategoryId : 2580c7b8-5ed9-11eb-85bc-0ae3c2aa3024
     * menuCategoryName : Tasty Treat
     * menuSubCategory : []
     * menuProducts : [{"id":"2580c7b8-5ed9-11eb-85bc-0ae3c2aa3024","menuSubCategoryId":0,"originalAmount1":15.99,"menuProductPrice":15.99,"menuId":0,"amount":15.99,"productModifiers":[],"productName":"Tasty Treat","mealProducts":[{"amount":15.99,"quantity":"1","oroginalQuantity":"1","originalAmount":15.99,"productSizeId":"db5033ae-5659-11eb-ba98-0ae3c2aa3024","originalAmount1":15.99,"productSizePrice":15.99,"sizeModifiers":[{"modifierType":"paid","sizeModifierProducts":[{"unit":null,"amount":0,"quantity":1,"productId":"db345ea4-5659-11eb-854a-0ae3c2aa3024","productName":"Pizza Garlic Bread","originalAmount1":0,"modifierProductPrice":0,"originalQuantity":1},{"unit":"other","amount":1.3,"quantity":1,"productId":"ed97b7a2-87bf-11eb-8a19-0ae3c2aa3024","originalAmount1":1.3,"productName":"Stuffed Crust Cheese","modifierProductPrice":1.3,"originalQuantity":1}],"minAllowedQuantity":1,"maxAllowedQuantity":1,"modifierId":"db510004-5659-11eb-83f2-0ae3c2aa3024","modifierName":"Crust"},{"modifierType":"paid","sizeModifierProducts":[{"unit":"other","quantity":5,"amount":0.1,"productId":"ed9ca76c-87bf-11eb-94b0-0ae3c2aa3024","productName":"BBQ Chicken","originalAmount1":0.1,"modifierProductPrice":0.1,"originalQuantity":5},{"amount":0,"unit":null,"quantity":1,"productId":"db345ea4-5659-11eb-854a-0ae3c2aa3024","productName":"Pizza Garlic Bread","originalAmount1":0,"modifierProductPrice":0,"originalQuantity":1},{"unit":"Pizza","quantity":1,"amount":0,"productId":"ed792eea-87bf-11eb-add9-0ae3c2aa3024","productName":"Thin Crust","originalAmount1":0,"modifierProductPrice":0,"originalQuantity":1},{"unit":"other","quantity":5,"amount":1,"productId":"ed8009ae-87bf-11eb-b48e-0ae3c2aa3024","originalAmount1":1,"productName":"Chicken Tikka","modifierProductPrice":1,"originalQuantity":5},{"unit":null,"quantity":1,"amount":0,"productId":"ee0afa74-5659-11eb-a5ff-0ae3c2aa3024","originalAmount1":0,"productName":"Margherita Pizza","modifierProductPrice":0,"originalQuantity":1},{"unit":"Pizza","quantity":1,"amount":0,"productId":"ed792eea-87bf-11eb-add9-0ae3c2aa3024","originalAmount1":0,"productName":"Thin Crust","modifierProductPrice":0,"originalQuantity":1},{"unit":"other","quantity":1,"amount":2.5,"productId":"ed8009ae-87bf-11eb-b48e-0ae3c2aa3024","originalAmount1":2.5,"productName":"Chicken Tikka","modifierProductPrice":2.5,"originalQuantity":1},{"amount":0.625,"quantity":4,"unit":"other","productId":"ed80f710-87bf-11eb-9e05-0ae3c2aa3024","originalAmount1":0.625,"productName":"Donner","modifierProductPrice":0.625,"originalQuantity":4}],"maxAllowedQuantity":10,"minAllowedQuantity":0,"modifierId":"db55e934-5659-11eb-b490-0ae3c2aa3024","modifierName":"Toppings"}],"isSelected":true,"productSizeName":"Large"}],"quantity":1,"originalQuantity":1,"vegType":"Tasty Treat","menuProductId":"2580c7b8-5ed9-11eb-85bc-0ae3c2aa3024","originalAmount":15.99,"menuProductSize":[]}]
     * menuId : 0
     */


    private Integer id;
    private String menuCategoryId;
    private String menuCategoryName;
    private Integer menuId;
    private List<?> menuSubCategory;
    private List<MenuProductsDTO> menuProducts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuCategoryId() {
        return menuCategoryId;
    }

    public void setMenuCategoryId(String menuCategoryId) {
        this.menuCategoryId = menuCategoryId;
    }

    public String getMenuCategoryName() {
        return menuCategoryName;
    }

    public void setMenuCategoryName(String menuCategoryName) {
        this.menuCategoryName = menuCategoryName;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public List<?> getMenuSubCategory() {
        return menuSubCategory;
    }

    public void setMenuSubCategory(List<?> menuSubCategory) {
        this.menuSubCategory = menuSubCategory;
    }

    public List<MenuProductsDTO> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(List<MenuProductsDTO> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public static class MenuProductsDTO implements Serializable {
        /**
         * id : 2580c7b8-5ed9-11eb-85bc-0ae3c2aa3024
         * menuSubCategoryId : 0
         * originalAmount1 : 15.99
         * menuProductPrice : 15.99
         * menuId : 0
         * amount : 15.99
         * productModifiers : []
         * productName : Tasty Treat
         * mealProducts : [{"amount":15.99,"quantity":"1","oroginalQuantity":"1","originalAmount":15.99,"productSizeId":"db5033ae-5659-11eb-ba98-0ae3c2aa3024","originalAmount1":15.99,"productSizePrice":15.99,"sizeModifiers":[{"modifierType":"paid","sizeModifierProducts":[{"unit":null,"amount":0,"quantity":1,"productId":"db345ea4-5659-11eb-854a-0ae3c2aa3024","productName":"Pizza Garlic Bread","originalAmount1":0,"modifierProductPrice":0,"originalQuantity":1},{"unit":"other","amount":1.3,"quantity":1,"productId":"ed97b7a2-87bf-11eb-8a19-0ae3c2aa3024","originalAmount1":1.3,"productName":"Stuffed Crust Cheese","modifierProductPrice":1.3,"originalQuantity":1}],"minAllowedQuantity":1,"maxAllowedQuantity":1,"modifierId":"db510004-5659-11eb-83f2-0ae3c2aa3024","modifierName":"Crust"},{"modifierType":"paid","sizeModifierProducts":[{"unit":"other","quantity":5,"amount":0.1,"productId":"ed9ca76c-87bf-11eb-94b0-0ae3c2aa3024","productName":"BBQ Chicken","originalAmount1":0.1,"modifierProductPrice":0.1,"originalQuantity":5},{"amount":0,"unit":null,"quantity":1,"productId":"db345ea4-5659-11eb-854a-0ae3c2aa3024","productName":"Pizza Garlic Bread","originalAmount1":0,"modifierProductPrice":0,"originalQuantity":1},{"unit":"Pizza","quantity":1,"amount":0,"productId":"ed792eea-87bf-11eb-add9-0ae3c2aa3024","productName":"Thin Crust","originalAmount1":0,"modifierProductPrice":0,"originalQuantity":1},{"unit":"other","quantity":5,"amount":1,"productId":"ed8009ae-87bf-11eb-b48e-0ae3c2aa3024","originalAmount1":1,"productName":"Chicken Tikka","modifierProductPrice":1,"originalQuantity":5},{"unit":null,"quantity":1,"amount":0,"productId":"ee0afa74-5659-11eb-a5ff-0ae3c2aa3024","originalAmount1":0,"productName":"Margherita Pizza","modifierProductPrice":0,"originalQuantity":1},{"unit":"Pizza","quantity":1,"amount":0,"productId":"ed792eea-87bf-11eb-add9-0ae3c2aa3024","originalAmount1":0,"productName":"Thin Crust","modifierProductPrice":0,"originalQuantity":1},{"unit":"other","quantity":1,"amount":2.5,"productId":"ed8009ae-87bf-11eb-b48e-0ae3c2aa3024","originalAmount1":2.5,"productName":"Chicken Tikka","modifierProductPrice":2.5,"originalQuantity":1},{"amount":0.625,"quantity":4,"unit":"other","productId":"ed80f710-87bf-11eb-9e05-0ae3c2aa3024","originalAmount1":0.625,"productName":"Donner","modifierProductPrice":0.625,"originalQuantity":4}],"maxAllowedQuantity":10,"minAllowedQuantity":0,"modifierId":"db55e934-5659-11eb-b490-0ae3c2aa3024","modifierName":"Toppings"}],"isSelected":true,"productSizeName":"Large"}]
         * quantity : 1
         * originalQuantity : 1
         * vegType : Tasty Treat
         * menuProductId : 2580c7b8-5ed9-11eb-85bc-0ae3c2aa3024
         * originalAmount : 15.99
         * menuProductSize : []
         */


        private String id;
        private Integer menuSubCategoryId;
        private Double originalAmount1;
        private Double menuProductPrice;
        private Integer menuId;
        private Double amount;
        private String productName;
        private Integer quantity;
        private Integer originalQuantity;
        private String vegType;
        private String menuProductId;
        private Double originalAmount;
        private List<?> productModifiers;
        private List<MealProductsDTO> mealProducts;
        private List<?> menuProductSize;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getMenuSubCategoryId() {
            return menuSubCategoryId;
        }

        public void setMenuSubCategoryId(Integer menuSubCategoryId) {
            this.menuSubCategoryId = menuSubCategoryId;
        }

        public Double getOriginalAmount1() {
            return originalAmount1;
        }

        public void setOriginalAmount1(Double originalAmount1) {
            this.originalAmount1 = originalAmount1;
        }

        public Double getMenuProductPrice() {
            return menuProductPrice;
        }

        public void setMenuProductPrice(Double menuProductPrice) {
            this.menuProductPrice = menuProductPrice;
        }

        public Integer getMenuId() {
            return menuId;
        }

        public void setMenuId(Integer menuId) {
            this.menuId = menuId;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Integer getOriginalQuantity() {
            return originalQuantity;
        }

        public void setOriginalQuantity(Integer originalQuantity) {
            this.originalQuantity = originalQuantity;
        }

        public String getVegType() {
            return vegType;
        }

        public void setVegType(String vegType) {
            this.vegType = vegType;
        }

        public String getMenuProductId() {
            return menuProductId;
        }

        public void setMenuProductId(String menuProductId) {
            this.menuProductId = menuProductId;
        }

        public Double getOriginalAmount() {
            return originalAmount;
        }

        public void setOriginalAmount(Double originalAmount) {
            this.originalAmount = originalAmount;
        }

        public List<?> getProductModifiers() {
            return productModifiers;
        }

        public void setProductModifiers(List<?> productModifiers) {
            this.productModifiers = productModifiers;
        }

        public List<MealProductsDTO> getMealProducts() {
            return mealProducts;
        }

        public void setMealProducts(List<MealProductsDTO> mealProducts) {
            this.mealProducts = mealProducts;
        }

        public List<?> getMenuProductSize() {
            return menuProductSize;
        }

        public void setMenuProductSize(List<?> menuProductSize) {
            this.menuProductSize = menuProductSize;
        }

        public static class MealProductsDTO implements Serializable {
            /**
             * amount : 15.99
             * quantity : 1
             * oroginalQuantity : 1
             * originalAmount : 15.99
             * productSizeId : db5033ae-5659-11eb-ba98-0ae3c2aa3024
             * originalAmount1 : 15.99
             * productSizePrice : 15.99
             * sizeModifiers : [{"modifierType":"paid","sizeModifierProducts":[{"unit":null,"amount":0,"quantity":1,"productId":"db345ea4-5659-11eb-854a-0ae3c2aa3024","productName":"Pizza Garlic Bread","originalAmount1":0,"modifierProductPrice":0,"originalQuantity":1},{"unit":"other","amount":1.3,"quantity":1,"productId":"ed97b7a2-87bf-11eb-8a19-0ae3c2aa3024","originalAmount1":1.3,"productName":"Stuffed Crust Cheese","modifierProductPrice":1.3,"originalQuantity":1}],"minAllowedQuantity":1,"maxAllowedQuantity":1,"modifierId":"db510004-5659-11eb-83f2-0ae3c2aa3024","modifierName":"Crust"},{"modifierType":"paid","sizeModifierProducts":[{"unit":"other","quantity":5,"amount":0.1,"productId":"ed9ca76c-87bf-11eb-94b0-0ae3c2aa3024","productName":"BBQ Chicken","originalAmount1":0.1,"modifierProductPrice":0.1,"originalQuantity":5},{"amount":0,"unit":null,"quantity":1,"productId":"db345ea4-5659-11eb-854a-0ae3c2aa3024","productName":"Pizza Garlic Bread","originalAmount1":0,"modifierProductPrice":0,"originalQuantity":1},{"unit":"Pizza","quantity":1,"amount":0,"productId":"ed792eea-87bf-11eb-add9-0ae3c2aa3024","productName":"Thin Crust","originalAmount1":0,"modifierProductPrice":0,"originalQuantity":1},{"unit":"other","quantity":5,"amount":1,"productId":"ed8009ae-87bf-11eb-b48e-0ae3c2aa3024","originalAmount1":1,"productName":"Chicken Tikka","modifierProductPrice":1,"originalQuantity":5},{"unit":null,"quantity":1,"amount":0,"productId":"ee0afa74-5659-11eb-a5ff-0ae3c2aa3024","originalAmount1":0,"productName":"Margherita Pizza","modifierProductPrice":0,"originalQuantity":1},{"unit":"Pizza","quantity":1,"amount":0,"productId":"ed792eea-87bf-11eb-add9-0ae3c2aa3024","originalAmount1":0,"productName":"Thin Crust","modifierProductPrice":0,"originalQuantity":1},{"unit":"other","quantity":1,"amount":2.5,"productId":"ed8009ae-87bf-11eb-b48e-0ae3c2aa3024","originalAmount1":2.5,"productName":"Chicken Tikka","modifierProductPrice":2.5,"originalQuantity":1},{"amount":0.625,"quantity":4,"unit":"other","productId":"ed80f710-87bf-11eb-9e05-0ae3c2aa3024","originalAmount1":0.625,"productName":"Donner","modifierProductPrice":0.625,"originalQuantity":4}],"maxAllowedQuantity":10,"minAllowedQuantity":0,"modifierId":"db55e934-5659-11eb-b490-0ae3c2aa3024","modifierName":"Toppings"}]
             * isSelected : true
             * productSizeName : Large
             */

            private Double amount;
            private String quantity;
            private String oroginalQuantity;
            private Double originalAmount;
            private String productSizeId;
            private Double originalAmount1;
            private Double productSizePrice;
            private Boolean isSelected;
            private String productSizeName;
            private List<SizeModifiersDTO> sizeModifiers;

            public Double getAmount() {
                return amount;
            }

            public void setAmount(Double amount) {
                this.amount = amount;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getOroginalQuantity() {
                return oroginalQuantity;
            }

            public void setOroginalQuantity(String oroginalQuantity) {
                this.oroginalQuantity = oroginalQuantity;
            }

            public Double getOriginalAmount() {
                return originalAmount;
            }

            public void setOriginalAmount(Double originalAmount) {
                this.originalAmount = originalAmount;
            }

            public String getProductSizeId() {
                return productSizeId;
            }

            public void setProductSizeId(String productSizeId) {
                this.productSizeId = productSizeId;
            }

            public Double getOriginalAmount1() {
                return originalAmount1;
            }

            public void setOriginalAmount1(Double originalAmount1) {
                this.originalAmount1 = originalAmount1;
            }

            public Double getProductSizePrice() {
                return productSizePrice;
            }

            public void setProductSizePrice(Double productSizePrice) {
                this.productSizePrice = productSizePrice;
            }

            public Boolean getSelected() {
                return isSelected;
            }

            public void setSelected(Boolean selected) {
                isSelected = selected;
            }

            public String getProductSizeName() {
                return productSizeName;
            }

            public void setProductSizeName(String productSizeName) {
                this.productSizeName = productSizeName;
            }

            public List<SizeModifiersDTO> getSizeModifiers() {
                return sizeModifiers;
            }

            public void setSizeModifiers(List<SizeModifiersDTO> sizeModifiers) {
                this.sizeModifiers = sizeModifiers;
            }

            public static class SizeModifiersDTO implements Serializable {
                /**
                 * modifierType : paid
                 * sizeModifierProducts : [{"unit":null,"amount":0,"quantity":1,"productId":"db345ea4-5659-11eb-854a-0ae3c2aa3024","productName":"Pizza Garlic Bread","originalAmount1":0,"modifierProductPrice":0,"originalQuantity":1},{"unit":"other","amount":1.3,"quantity":1,"productId":"ed97b7a2-87bf-11eb-8a19-0ae3c2aa3024","originalAmount1":1.3,"productName":"Stuffed Crust Cheese","modifierProductPrice":1.3,"originalQuantity":1}]
                 * minAllowedQuantity : 1
                 * maxAllowedQuantity : 1
                 * modifierId : db510004-5659-11eb-83f2-0ae3c2aa3024
                 * modifierName : Crust
                 */


                private String modifierType;
                private Integer minAllowedQuantity;
                private Integer maxAllowedQuantity;
                private String modifierId;
                private String modifierName;
                private List<SizeModifierProductsDTO> sizeModifierProducts;

                public String getModifierType() {
                    return modifierType;
                }

                public void setModifierType(String modifierType) {
                    this.modifierType = modifierType;
                }

                public Integer getMinAllowedQuantity() {
                    return minAllowedQuantity;
                }

                public void setMinAllowedQuantity(Integer minAllowedQuantity) {
                    this.minAllowedQuantity = minAllowedQuantity;
                }

                public Integer getMaxAllowedQuantity() {
                    return maxAllowedQuantity;
                }

                public void setMaxAllowedQuantity(Integer maxAllowedQuantity) {
                    this.maxAllowedQuantity = maxAllowedQuantity;
                }

                public String getModifierId() {
                    return modifierId;
                }

                public void setModifierId(String modifierId) {
                    this.modifierId = modifierId;
                }

                public String getModifierName() {
                    return modifierName;
                }

                public void setModifierName(String modifierName) {
                    this.modifierName = modifierName;
                }

                public List<SizeModifierProductsDTO> getSizeModifierProducts() {
                    return sizeModifierProducts;
                }

                public void setSizeModifierProducts(List<SizeModifierProductsDTO> sizeModifierProducts) {
                    this.sizeModifierProducts = sizeModifierProducts;
                }

                public static class SizeModifierProductsDTO implements Serializable {
                    /**
                     * unit : null
                     * amount : 0
                     * quantity : 1
                     * productId : db345ea4-5659-11eb-854a-0ae3c2aa3024
                     * productName : Pizza Garlic Bread
                     * originalAmount1 : 0
                     * modifierProductPrice : 0
                     * originalQuantity : 1
                     */


                    private Object unit;
                    private Double amount;
                    private Integer quantity;
                    private String productId;
                    private String productName;
                    private Double originalAmount1;
                    private Double modifierProductPrice;
                    private Integer originalQuantity;
                    private Integer max_allowed_quantity;

                    public Integer getMax_allowed_quantity() {
                        return max_allowed_quantity;
                    }

                    public void setMax_allowed_quantity(Integer max_allowed_quantity) {
                        this.max_allowed_quantity = max_allowed_quantity;
                    }

                    public Object getUnit() {
                        return unit;
                    }

                    public void setUnit(Object unit) {
                        this.unit = unit;
                    }

                    public Double getAmount() {
                        return amount;
                    }

                    public void setAmount(Double amount) {
                        this.amount = amount;
                    }

                    public Integer getQuantity() {
                        return quantity;
                    }

                    public void setQuantity(Integer quantity) {
                        this.quantity = quantity;
                    }

                    public String getProductId() {
                        return productId;
                    }

                    public void setProductId(String productId) {
                        this.productId = productId;
                    }

                    public String getProductName() {
                        return productName;
                    }

                    public void setProductName(String productName) {
                        this.productName = productName;
                    }

                    public Double getOriginalAmount1() {
                        return originalAmount1;
                    }

                    public void setOriginalAmount1(Double originalAmount1) {
                        this.originalAmount1 = originalAmount1;
                    }

                    public Double getModifierProductPrice() {
                        return modifierProductPrice;
                    }

                    public void setModifierProductPrice(Double modifierProductPrice) {
                        this.modifierProductPrice = modifierProductPrice;
                    }

                    public Integer getOriginalQuantity() {
                        return originalQuantity;
                    }

                    public void setOriginalQuantity(Integer originalQuantity) {
                        this.originalQuantity = originalQuantity;
                    }
                }
            }
        }
    }
}
