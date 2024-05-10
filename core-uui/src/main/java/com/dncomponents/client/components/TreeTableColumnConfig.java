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

package com.dncomponents.client.components;

import com.dncomponents.client.components.core.CellContext;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.tree.TreeNode;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class TreeTableColumnConfig<T, M> extends ColumnConfig<TreeNode<T>, M> {
    public TreeTableColumnConfig(Function<T, M> fieldGetter) {
        super(tn -> {
            T userObject;
            try {
                userObject = tn.getUserObject();
                return fieldGetter.apply(userObject);
            } catch (Exception ex) {
                //cast exception
                return null;
            }
        });
    }

    public TreeTableColumnConfig(ColumnConfig<T, M> columnConfig) {
        this(columnConfig.getFieldGetter());
        setFieldSetter((tn, m) -> {
            T userObject;
            try {
                userObject = tn.getUserObject();
                columnConfig.getFieldSetter().accept(userObject, m);
            } catch (Exception ex) {
                //cast exception
            }
        });
        setName(columnConfig.getName());
        setClazz(columnConfig.getClazz());
        setEditable(columnConfig.isEditable());
        setColumnWidth(columnConfig.getColumnWidth());
        setCellFactory(c -> {
            final CellContext<T, M> context = new CellContext<>(columnConfig, null, c.model.getUserObject(), (Table<T>) c.owner);
            return (TableCell<TreeNode<T>, M>) columnConfig.getCellFactory().getCell(context);
        });
        setHeaderCellFactory(() -> columnConfig.getHeaderCellFactory().getCell());
//        setComparator((o1, o2) ->
//                getComparator2(columnConfig.getFieldGetter())
//                        .compare(o1.getUserObject(), o2.getUserObject()));
    }

    public TreeTableColumnConfig(Function<T, M> fieldGetter, BiConsumer<T, M> fieldSetter) {
        this(fieldGetter);
        setFieldSetter((tn, m) -> {
            T userObject;
            try {
                userObject = tn.getUserObject();
                fieldSetter.accept(userObject, m);
            } catch (Exception ex) {
                //cast exception
            }
        });
    }


    private static <T> Optional<T> getUserObject(TreeNode<T> node) {
        T userObject;
        try {
            userObject = node.getUserObject();
        } catch (Exception ex) {
            //class cast exception
            userObject = null;
        }
        return Optional.ofNullable(userObject);
    }
}
