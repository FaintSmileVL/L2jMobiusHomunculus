package org.l2jmobius.gameserver.data.xml;

import org.jdom2.Content;
import org.jdom2.Element;
import org.l2jmobius.gameserver.data.XMLWriter;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;

/**
 * @author : Alice
 * @date : 22.12.2021
 * @time : 17:34
 */
public class NpcDataWrite {
   /* public static void write() {
        String fileNamePartOne;
        String fileNamePartTwo;
        for (NpcTemplate npcData : NpcData.getInstance().getTemplates(npc -> npc.getId() > 0)) {
            XMLWriter.getInstance().init();
            for(Content content : generate(npcData).removeContent()) {
                XMLWriter.getInstance().getRootElement().addContent(content);
            }
            XMLWriter.getInstance().writeToXML(npcData.getName());
        }
    }

    public static Element generate(NpcTemplate npcData) {
        Element npclist = new Element("list");
        // Создаем элемент head и добавляем ему атрибут
        Element npc = new Element("npc");

        npc.setAttribute("id", String.valueOf(npcData.npc_class_id));
        if (npcData.fake_class_id != -1) {
            npc.setAttribute("displayId", String.valueOf(npcData.fake_class_id));
        }
        npc.setAttribute("level", String.valueOf(npcData.level));
        npc.setAttribute("type", String.valueOf(npcData.getNpcType().name()));
        npc.setAttribute("name", String.valueOf(npcData.npc_name));
        npc.setAttribute("title", String.valueOf(npcData.titl));

        npclist.addContent(npc);
        int id = 1;
        for (MultiSellHolder.Item item : npcData.getItemArrayList()) {
            Element item_el = new Element("item");
            item_el.setAttribute("id", String.valueOf(id));
            id++;

            for (MultiSellHolder.Ingredient ingredient : item.getIngredientArrayList()) {
                Element ingredient_el = new Element("ingredient");
                int newId = ingredient.getId();
                for (Pair<Integer, Integer> pair : FreeItemIdChecker.getInstance().getSqlList()) {
                    if (newId == pair.getKey()) {
                        newId = pair.getValue();
                        break;
                    }
                }

                if (newId == 32282 || newId  == 32284) {
                    continue;
                }
                ingredient_el.setAttribute("id", String.valueOf(newId));
                ingredient_el.setAttribute("count", String.valueOf(ingredient.getCount()));
                if (ingredient.getEnchant() > 0) {
                    ingredient_el.setAttribute("enchant", String.valueOf(ingredient.getEnchant()));
                }
                if (ingredient.isMantainIngredient()) {
                    ingredient_el.setAttribute("mantainIngredient", String.valueOf(ingredient.isMantainIngredient()));
                }
                if (ingredient.getFireAttr() > 0) {
                    ingredient_el.setAttribute("fireAttr", String.valueOf(ingredient.getFireAttr()));
                }
                if (ingredient.getWaterAttr() > 0) {
                    ingredient_el.setAttribute("waterAttr", String.valueOf(ingredient.getWaterAttr()));
                }
                if (ingredient.getEarthAttr() > 0) {
                    ingredient_el.setAttribute("earthAttr", String.valueOf(ingredient.getEarthAttr()));
                }
                if (ingredient.getWindAttr() > 0) {
                    ingredient_el.setAttribute("windAttr", String.valueOf(ingredient.getWindAttr()));
                }
                if (ingredient.getHolyAttr() > 0) {
                    ingredient_el.setAttribute("holyAttr", String.valueOf(ingredient.getHolyAttr()));
                }
                if (ingredient.getUnholyAttr() > 0) {
                    ingredient_el.setAttribute("unholyAttr", String.valueOf(ingredient.getUnholyAttr()));
                }
                item_el.addContent(ingredient_el);
            }
            for (MultiSellHolder.Ingredient product : item.getProductArrayList()) {
                Element product_el = new Element("production");
                int newId = product.getId();
                for (Pair<Integer, Integer> pair : FreeItemIdChecker.getInstance().getSqlList()) {
                    if (newId == pair.getKey()) {
                        newId = pair.getValue();
                        break;
                    }
                }
                product_el.setAttribute("id", String.valueOf(newId));
                product_el.setAttribute("count", String.valueOf(product.getCount()));
                if (product.getEnchant() > 0) {
                    product_el.setAttribute("enchant", String.valueOf(product.getEnchant()));
                }
                if (product.getFireAttr() > 0) {
                    product_el.setAttribute("fireAttr", String.valueOf(product.getFireAttr()));
                }
                if (product.getWaterAttr() > 0) {
                    product_el.setAttribute("waterAttr", String.valueOf(product.getWaterAttr()));
                }
                if (product.getEarthAttr() > 0) {
                    product_el.setAttribute("earthAttr", String.valueOf(product.getEarthAttr()));
                }
                if (product.getWindAttr() > 0) {
                    product_el.setAttribute("windAttr", String.valueOf(product.getWindAttr()));
                }
                if (product.getHolyAttr() > 0) {
                    product_el.setAttribute("holyAttr", String.valueOf(product.getHolyAttr()));
                }
                if (product.getUnholyAttr() > 0) {
                    product_el.setAttribute("unholyAttr", String.valueOf(product.getUnholyAttr()));
                }
                item_el.addContent(product_el);
            }
            npclist.addContent(item_el);
        }
        return npclist;
    }*/
}
