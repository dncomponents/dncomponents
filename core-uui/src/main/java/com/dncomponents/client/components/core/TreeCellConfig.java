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

package com.dncomponents.client.components.core;

import com.dncomponents.client.components.tree.TreeCellFactory;
import com.dncomponents.client.components.tree.TreeNode;

import java.util.function.BiConsumer;
import java.util.function.Function;


public class TreeCellConfig<T, M> extends CellConfig<TreeNode<T>, M> {

    protected CellRenderer<TreeNode, T> renderer;

    {
        setCellFactory(c -> c.createDefaultCell());
    }

    public TreeCellConfig() {
        super(tTreeNode -> ((M) tTreeNode.getUserObject()));
    }

    public TreeCellConfig(Function<TreeNode<T>, M> fieldGetter) {
        super(fieldGetter);
    }

    public TreeCellConfig(Function<TreeNode<T>, M> fieldGetter, BiConsumer<TreeNode<T>, M> fieldSetter) {
        super(fieldGetter, fieldSetter);
    }

    public TreeCellConfig<T, M> setCellFactory(TreeCellFactory<T, M> cellFactory) {
        super.setCellFactory(cellFactory);
        return this;
    }

}
