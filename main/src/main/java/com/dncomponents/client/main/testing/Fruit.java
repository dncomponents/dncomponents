/*
 * Copyright 2024 dncomponents
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dncomponents.client.main.testing;

import com.dncomponents.client.components.tree.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Fruit {
    public static List<Fruit> fruits = Arrays.asList(
            new Fruit("Bell pepper", "Non-sweet"),
            new Fruit("Cucumber", "Non-sweet"),
            new Fruit("Tomato", "Non-sweet"),
            //Sub-acid
            new Fruit("Apple", "Sub-acid"),
            new Fruit("Apricot", "Sub-acid"),
            new Fruit("Blackberry", "Sub-acid"),
            new Fruit("Cherry", "Sub-acid"),
            //Sweet
            new Fruit("Banana", "Sweet"),
            new Fruit("Papaya", "Sweet"),
            new Fruit("Prune", "Sweet"),
            //Acid
            new Fruit("Kiwi", "Acid"),
            new Fruit("Lemon", "Acid"),
            new Fruit("Orange", "Acid")
    );


    private String name;
    private String description;

    public Fruit() {
    }

    public Fruit(String name, String description) {
        this.description = description;
        this.name = name;
    }

    public static List<Fruit> getFruits(int number) {
        List<Fruit> fruitList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            Fruit fruit = fruits.get(random.nextInt(fruits.size()));
            fruitList.add(new Fruit(fruit.getName() + "  " + i, "The " + fruit.getName()
                                                                + " is a " + fruit.getDescription() + " fruit"));
        }
        return fruitList;
    }


    public static TreeNode<Object> getFruitsTree() {
        TreeNode<Object> root = new TreeNode<>("root");
        {
            TreeNode<Object> node = new TreeNode<>("Non-sweet");
            node.add(new TreeNode(new Fruit("Avocado", "fruit")));
            node.add(new TreeNode(new Fruit("Bell pepper", "fruit")));
            node.add(new TreeNode(new Fruit("Cucumber", "fruit")));
            node.add(new TreeNode(new Fruit("Tomato", "fruit")));
            root.add(node);
        }
        {
            TreeNode<Object> node = new TreeNode<>("Sub-acid");
            node.add(new TreeNode(new Fruit("Apple", "fruit")));
            node.add(new TreeNode(new Fruit("Apricot", "fruit")));
            node.add(new TreeNode(new Fruit("Blackberry", "fruit")));
            node.add(new TreeNode(new Fruit("Cherry", "fruit")));
            root.add(node);
        }
        {
            TreeNode<Object> node = new TreeNode<>("Sweet");
            node.add(new TreeNode(new Fruit("Banana", "fruit")));
            node.add(new TreeNode(new Fruit("Papaya", "fruit")));
            node.add(new TreeNode(new Fruit("Prune", "fruit")));
            root.add(node);
        }
        {
            TreeNode<Object> node = new TreeNode<>("Acid");
            node.add(new TreeNode(new Fruit("Kiwi", "fruit")));
            node.add(new TreeNode(new Fruit("Lemon", "fruit")));
            node.add(new TreeNode(new Fruit("Orange", "fruit")));
            root.add(node);
        }
        return root;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
