package com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response;

import java.util.List;

public class MeaListBean {



    private boolean success;
    private String message;


    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {


        private MealBean meal;


        private List<MealConfigBean> meal_config;

        public MealBean getMeal() {
            return meal;
        }

        public void setMeal(MealBean meal) {
            this.meal = meal;
        }

        public List<MealConfigBean> getMeal_config() {
            return meal_config;
        }

        public void setMeal_config(List<MealConfigBean> meal_config) {
            this.meal_config = meal_config;
        }

        public static class MealBean {
            private String id;
            private String restaurant_id;
            private String meal_name;
            private String meal_price;
            private String veg_type;
            private String menu_category_id;
            private String description;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRestaurant_id() {
                return restaurant_id;
            }

            public void setRestaurant_id(String restaurant_id) {
                this.restaurant_id = restaurant_id;
            }

            public String getMeal_name() {
                return meal_name;
            }

            public void setMeal_name(String meal_name) {
                this.meal_name = meal_name;
            }

            public String getMeal_price() {
                return meal_price;
            }

            public void setMeal_price(String meal_price) {
                this.meal_price = meal_price;
            }

            public String getVeg_type() {
                return veg_type;
            }

            public void setVeg_type(String veg_type) {
                this.veg_type = veg_type;
            }

            public String getMenu_category_id() {
                return menu_category_id;
            }

            public void setMenu_category_id(String menu_category_id) {
                this.menu_category_id = menu_category_id;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }

        public static class MealConfigBean {
            private String name;
            private String product_size_name;
            private int product_quantity;
            private int allowed_quantity;
            private int is_customizable;
            private String selling_price;
            private String categoryslugname;
            /**
             * menu_product_id : db345ea4-5659-11eb-854a-0ae3c2aa3024
             * product_name : Pizza Garlic Bread
             * description :
             * veg_type : mixed
             * menu_product_price : 7.99
             * userapp_product_image : https://admin.easyfood.co.uk/app-assets/images/fast-food-product-default.png
             * ecom_product_image : https://admin.easyfood.co.uk/app-assets/images/fast-food-product-default.png
             * product_overall_rating : 2
             * eat_in : 1
             * take_away : 1
             * proudct_delivery : 1
             * size_title : Size
             * menu_product_size : [{"product_size_id":"db5033ae-5659-11eb-ba98-0ae3c2aa3024","product_size_name":"Large","product_size_price":"7.99","size_modifiers":[{"modifier_name":"Crust","modifier_type":"paid","modifier_id":"db510004-5659-11eb-83f2-0ae3c2aa3024","min_allowed_quantity":1,"max_allowed_quantity":1,"free_qty_limit":0,"size_modifier_products":[{"product_id":"ed957d66-87bf-11eb-aec8-0ae3c2aa3024","unit":"other","modifier_product_price":"0.00","description":"Deep Pan","product_name":"Deep Pan"},{"product_id":"ed96ae7a-87bf-11eb-b5a4-0ae3c2aa3024","unit":"other","modifier_product_price":"0.00","description":"Pepperoni Crust","product_name":"Pepperoni Crust"},{"product_id":"ed97b7a2-87bf-11eb-8a19-0ae3c2aa3024","unit":"other","modifier_product_price":"1.30","description":"Stuffed Crust Cheese","product_name":"Stuffed Crust Cheese"},{"product_id":"ed98bc6a-87bf-11eb-895d-0ae3c2aa3024","unit":"Pizza","modifier_product_price":"0.00","description":"sadfsdf","product_name":"Thin Crust"}]},{"modifier_name":"Toppings","modifier_type":"paid","modifier_id":"db55e934-5659-11eb-b490-0ae3c2aa3024","min_allowed_quantity":0,"max_allowed_quantity":10,"free_qty_limit":3,"size_modifier_products":[{"product_id":"ed9ca76c-87bf-11eb-94b0-0ae3c2aa3024","unit":"other","modifier_product_price":"0.25","description":"BBQ Chicken","product_name":"BBQ Chicken"},{"product_id":"ed9d7a7a-87bf-11eb-ba71-0ae3c2aa3024","unit":"other","modifier_product_price":"0.30","description":"Black Olives","product_name":"Black Olives"},{"product_id":"ed9ed7da-87bf-11eb-b54f-0ae3c2aa3024","unit":"other","modifier_product_price":"0.00","description":"Chicken","product_name":"Chicken"},{"product_id":"ed9fee2c-87bf-11eb-81f1-0ae3c2aa3024","unit":"other","modifier_product_price":"1.00","description":"Chicken Tikka","product_name":"Chicken Tikka"},{"product_id":"eda0d328-87bf-11eb-92fc-0ae3c2aa3024","unit":"other","modifier_product_price":"1.50","description":"Donner","product_name":"Donner"}]}]}]
             * product_modifiers : []
             * selling_price : 0.00
             * is_customizable : 1
             * category_name_slug : largepizza
             * category_name : Large Pizza
             * allowed_quantity : 1
             * product_size_name : Large
             * product_size_id : db5033ae-5659-11eb-ba98-0ae3c2aa3024
             * product_id : db345ea4-5659-11eb-854a-0ae3c2aa3024
             * id : 2581b326-5ed9-11eb-aa0f-0ae3c2aa3024
             */

            private List<ProductsBean> products;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getProduct_size_name() {
                return product_size_name;
            }

            public void setProduct_size_name(String product_size_name) {
                this.product_size_name = product_size_name;
            }

            public int getProduct_quantity() {
                return product_quantity;
            }

            public void setProduct_quantity(int product_quantity) {
                this.product_quantity = product_quantity;
            }

            public int getAllowed_quantity() {
                return allowed_quantity;
            }

            public void setAllowed_quantity(int allowed_quantity) {
                this.allowed_quantity = allowed_quantity;
            }

            public int getIs_customizable() {
                return is_customizable;
            }

            public void setIs_customizable(int is_customizable) {
                this.is_customizable = is_customizable;
            }

            public String getSelling_price() {
                return selling_price;
            }

            public void setSelling_price(String selling_price) {
                this.selling_price = selling_price;
            }

            public String getCategoryslugname() {
                return categoryslugname;
            }

            public void setCategoryslugname(String categoryslugname) {
                this.categoryslugname = categoryslugname;
            }

            public List<ProductsBean> getProducts() {
                return products;
            }

            public void setProducts(List<ProductsBean> products) {
                this.products = products;
            }

            public static class ProductsBean {
                private String menu_product_id;
                private String product_name;
                private String description;
                private String veg_type;
                private String menu_product_price;
                private String userapp_product_image;
                private String ecom_product_image;
                private String product_overall_rating;
                private int eat_in;
                private int take_away;
                private int proudct_delivery;
                private String size_title;
                private String selling_price;
                private int is_customizable;
                private String category_name_slug;
                private String category_name;
                private int allowed_quantity;
                private String product_size_name;
                private String product_size_id;
                private String product_id;
                private String id;
                /**
                 * product_size_id : db5033ae-5659-11eb-ba98-0ae3c2aa3024
                 * product_size_name : Large
                 * product_size_price : 7.99
                 * size_modifiers : [{"modifier_name":"Crust","modifier_type":"paid","modifier_id":"db510004-5659-11eb-83f2-0ae3c2aa3024","min_allowed_quantity":1,"max_allowed_quantity":1,"free_qty_limit":0,"size_modifier_products":[{"product_id":"ed957d66-87bf-11eb-aec8-0ae3c2aa3024","unit":"other","modifier_product_price":"0.00","description":"Deep Pan","product_name":"Deep Pan"},{"product_id":"ed96ae7a-87bf-11eb-b5a4-0ae3c2aa3024","unit":"other","modifier_product_price":"0.00","description":"Pepperoni Crust","product_name":"Pepperoni Crust"},{"product_id":"ed97b7a2-87bf-11eb-8a19-0ae3c2aa3024","unit":"other","modifier_product_price":"1.30","description":"Stuffed Crust Cheese","product_name":"Stuffed Crust Cheese"},{"product_id":"ed98bc6a-87bf-11eb-895d-0ae3c2aa3024","unit":"Pizza","modifier_product_price":"0.00","description":"sadfsdf","product_name":"Thin Crust"}]},{"modifier_name":"Toppings","modifier_type":"paid","modifier_id":"db55e934-5659-11eb-b490-0ae3c2aa3024","min_allowed_quantity":0,"max_allowed_quantity":10,"free_qty_limit":3,"size_modifier_products":[{"product_id":"ed9ca76c-87bf-11eb-94b0-0ae3c2aa3024","unit":"other","modifier_product_price":"0.25","description":"BBQ Chicken","product_name":"BBQ Chicken"},{"product_id":"ed9d7a7a-87bf-11eb-ba71-0ae3c2aa3024","unit":"other","modifier_product_price":"0.30","description":"Black Olives","product_name":"Black Olives"},{"product_id":"ed9ed7da-87bf-11eb-b54f-0ae3c2aa3024","unit":"other","modifier_product_price":"0.00","description":"Chicken","product_name":"Chicken"},{"product_id":"ed9fee2c-87bf-11eb-81f1-0ae3c2aa3024","unit":"other","modifier_product_price":"1.00","description":"Chicken Tikka","product_name":"Chicken Tikka"},{"product_id":"eda0d328-87bf-11eb-92fc-0ae3c2aa3024","unit":"other","modifier_product_price":"1.50","description":"Donner","product_name":"Donner"}]}]
                 */

                private List<MenuProductSizeBean> menu_product_size;
                private List<?> product_modifiers;

                public String getMenu_product_id() {
                    return menu_product_id;
                }

                public void setMenu_product_id(String menu_product_id) {
                    this.menu_product_id = menu_product_id;
                }

                public String getProduct_name() {
                    return product_name;
                }

                public void setProduct_name(String product_name) {
                    this.product_name = product_name;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getVeg_type() {
                    return veg_type;
                }

                public void setVeg_type(String veg_type) {
                    this.veg_type = veg_type;
                }

                public String getMenu_product_price() {
                    return menu_product_price;
                }

                public void setMenu_product_price(String menu_product_price) {
                    this.menu_product_price = menu_product_price;
                }

                public String getUserapp_product_image() {
                    return userapp_product_image;
                }

                public void setUserapp_product_image(String userapp_product_image) {
                    this.userapp_product_image = userapp_product_image;
                }

                public String getEcom_product_image() {
                    return ecom_product_image;
                }

                public void setEcom_product_image(String ecom_product_image) {
                    this.ecom_product_image = ecom_product_image;
                }

                public String getProduct_overall_rating() {
                    return product_overall_rating;
                }

                public void setProduct_overall_rating(String product_overall_rating) {
                    this.product_overall_rating = product_overall_rating;
                }

                public int getEat_in() {
                    return eat_in;
                }

                public void setEat_in(int eat_in) {
                    this.eat_in = eat_in;
                }

                public int getTake_away() {
                    return take_away;
                }

                public void setTake_away(int take_away) {
                    this.take_away = take_away;
                }

                public int getProudct_delivery() {
                    return proudct_delivery;
                }

                public void setProudct_delivery(int proudct_delivery) {
                    this.proudct_delivery = proudct_delivery;
                }

                public String getSize_title() {
                    return size_title;
                }

                public void setSize_title(String size_title) {
                    this.size_title = size_title;
                }

                public String getSelling_price() {
                    return selling_price;
                }

                public void setSelling_price(String selling_price) {
                    this.selling_price = selling_price;
                }

                public int getIs_customizable() {
                    return is_customizable;
                }

                public void setIs_customizable(int is_customizable) {
                    this.is_customizable = is_customizable;
                }

                public String getCategory_name_slug() {
                    return category_name_slug;
                }

                public void setCategory_name_slug(String category_name_slug) {
                    this.category_name_slug = category_name_slug;
                }

                public String getCategory_name() {
                    return category_name;
                }

                public void setCategory_name(String category_name) {
                    this.category_name = category_name;
                }

                public int getAllowed_quantity() {
                    return allowed_quantity;
                }

                public void setAllowed_quantity(int allowed_quantity) {
                    this.allowed_quantity = allowed_quantity;
                }

                public String getProduct_size_name() {
                    return product_size_name;
                }

                public void setProduct_size_name(String product_size_name) {
                    this.product_size_name = product_size_name;
                }

                public String getProduct_size_id() {
                    return product_size_id;
                }

                public void setProduct_size_id(String product_size_id) {
                    this.product_size_id = product_size_id;
                }

                public String getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(String product_id) {
                    this.product_id = product_id;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public List<MenuProductSizeBean> getMenu_product_size() {
                    return menu_product_size;
                }

                public void setMenu_product_size(List<MenuProductSizeBean> menu_product_size) {
                    this.menu_product_size = menu_product_size;
                }

                public List<?> getProduct_modifiers() {
                    return product_modifiers;
                }

                public void setProduct_modifiers(List<?> product_modifiers) {
                    this.product_modifiers = product_modifiers;
                }

                public static class MenuProductSizeBean {
                    private String product_size_id;
                    private String product_size_name;
                    private String product_size_price;
                    /**
                     * modifier_name : Crust
                     * modifier_type : paid
                     * modifier_id : db510004-5659-11eb-83f2-0ae3c2aa3024
                     * min_allowed_quantity : 1
                     * max_allowed_quantity : 1
                     * free_qty_limit : 0
                     * size_modifier_products : [{"product_id":"ed957d66-87bf-11eb-aec8-0ae3c2aa3024","unit":"other","modifier_product_price":"0.00","description":"Deep Pan","product_name":"Deep Pan"},{"product_id":"ed96ae7a-87bf-11eb-b5a4-0ae3c2aa3024","unit":"other","modifier_product_price":"0.00","description":"Pepperoni Crust","product_name":"Pepperoni Crust"},{"product_id":"ed97b7a2-87bf-11eb-8a19-0ae3c2aa3024","unit":"other","modifier_product_price":"1.30","description":"Stuffed Crust Cheese","product_name":"Stuffed Crust Cheese"},{"product_id":"ed98bc6a-87bf-11eb-895d-0ae3c2aa3024","unit":"Pizza","modifier_product_price":"0.00","description":"sadfsdf","product_name":"Thin Crust"}]
                     */

                    private List<SizeModifiersBean> size_modifiers;

                    public String getProduct_size_id() {
                        return product_size_id;
                    }

                    public void setProduct_size_id(String product_size_id) {
                        this.product_size_id = product_size_id;
                    }

                    public String getProduct_size_name() {
                        return product_size_name;
                    }

                    public void setProduct_size_name(String product_size_name) {
                        this.product_size_name = product_size_name;
                    }

                    public String getProduct_size_price() {
                        return product_size_price;
                    }

                    public void setProduct_size_price(String product_size_price) {
                        this.product_size_price = product_size_price;
                    }

                    public List<SizeModifiersBean> getSize_modifiers() {
                        return size_modifiers;
                    }

                    public void setSize_modifiers(List<SizeModifiersBean> size_modifiers) {
                        this.size_modifiers = size_modifiers;
                    }

                    public static class SizeModifiersBean {
                        private String modifier_name;
                        private String modifier_type;
                        private String modifier_id;
                        private int min_allowed_quantity;
                        private int max_allowed_quantity;
                        private int free_qty_limit;
                        /**
                         * product_id : ed957d66-87bf-11eb-aec8-0ae3c2aa3024
                         * unit : other
                         * modifier_product_price : 0.00
                         * description : Deep Pan
                         * product_name : Deep Pan
                         */

                        private List<SizeModifierProductsBean> size_modifier_products;

                        public String getModifier_name() {
                            return modifier_name;
                        }

                        public void setModifier_name(String modifier_name) {
                            this.modifier_name = modifier_name;
                        }

                        public String getModifier_type() {
                            return modifier_type;
                        }

                        public void setModifier_type(String modifier_type) {
                            this.modifier_type = modifier_type;
                        }

                        public String getModifier_id() {
                            return modifier_id;
                        }

                        public void setModifier_id(String modifier_id) {
                            this.modifier_id = modifier_id;
                        }

                        public int getMin_allowed_quantity() {
                            return min_allowed_quantity;
                        }

                        public void setMin_allowed_quantity(int min_allowed_quantity) {
                            this.min_allowed_quantity = min_allowed_quantity;
                        }

                        public int getMax_allowed_quantity() {
                            return max_allowed_quantity;
                        }

                        public void setMax_allowed_quantity(int max_allowed_quantity) {
                            this.max_allowed_quantity = max_allowed_quantity;
                        }

                        public int getFree_qty_limit() {
                            return free_qty_limit;
                        }

                        public void setFree_qty_limit(int free_qty_limit) {
                            this.free_qty_limit = free_qty_limit;
                        }

                        public List<SizeModifierProductsBean> getSize_modifier_products() {
                            return size_modifier_products;
                        }

                        public void setSize_modifier_products(List<SizeModifierProductsBean> size_modifier_products) {
                            this.size_modifier_products = size_modifier_products;
                        }

                        public static class SizeModifierProductsBean {
                            private String product_id;
                            private String unit;
                            private String modifier_product_price;
                            private String description;
                            private String product_name;

                            public String getProduct_id() {
                                return product_id;
                            }

                            public void setProduct_id(String product_id) {
                                this.product_id = product_id;
                            }

                            public String getUnit() {
                                return unit;
                            }

                            public void setUnit(String unit) {
                                this.unit = unit;
                            }

                            public String getModifier_product_price() {
                                return modifier_product_price;
                            }

                            public void setModifier_product_price(String modifier_product_price) {
                                this.modifier_product_price = modifier_product_price;
                            }

                            public String getDescription() {
                                return description;
                            }

                            public void setDescription(String description) {
                                this.description = description;
                            }

                            public String getProduct_name() {
                                return product_name;
                            }

                            public void setProduct_name(String product_name) {
                                this.product_name = product_name;
                            }
                        }
                    }
                }
            }
        }
    }
}
