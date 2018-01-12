package com.example.idarkduck.shopify_backend;

import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by iDarkDuck on 1/11/18.
 */

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreeNode<T> implements Iterable<TreeNode<T>> {

    private T data;
    private TreeNode<T> parent;
    private TreeNode<T> leftChild;
    private TreeNode<T> rightChild;

    public TreeNode(T data) {
        this.data = data;
        this.leftChild = new TreeNode<T>(null);
        this.rightChild = new TreeNode<T>(null);
    }

    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);
        childNode.parent = this;

        if ()

        this.children.add(childNode);
        this.registerChildForSearch(childNode);
        return childNode;
    }

    public int getLevel() {
        if (this.isRoot())
            return 0;
        else
            return parent.getLevel() + 1;
    }

    private void registerChildForSearch(TreeNode<T> node) {
        elementsIndex.add(node);
        if (parent != null)
            parent.registerChildForSearch(node);
    }

    public TreeNode<T> findTreeNode(Comparable<T> cmp) {
        for (TreeNode<T> element : this.elementsIndex) {
            T elData = element.data;
            if (cmp.compareTo(elData) == 0)
                return element;
        }

        return null;
    }

    public boolean searchTreeNode(T searching) {
        for (TreeNode<T> element : this.elementsIndex) {
            T elData = element.data;
            if ( searching.equals(elData) ) {
                return true;
            }
        }
        return false;
    }

    public boolean searchTreeNode2(T searching) {
        for (TreeNode<T> element : this.elementsIndex) {
            T elData = element.data;
            if ( searching == elData)  {
                return true;
            }
        }
        return false;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return (leftChild == null && rightChild == null);
    }

    @Override
    public String toString() {
        return data != null ? data.toString() : "[data null]";
    }

    @Override
    public Iterator<TreeNode<T>> iterator() {
        TreeNodeIter<T> iter = new TreeNodeIter<T>(this);
        return iter;
    }
}