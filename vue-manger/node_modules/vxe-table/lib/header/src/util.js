"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.convertToRows = void 0;

function _toConsumableArray(arr) { return _arrayWithoutHoles(arr) || _iterableToArray(arr) || _nonIterableSpread(); }

function _nonIterableSpread() { throw new TypeError("Invalid attempt to spread non-iterable instance"); }

function _iterableToArray(iter) { if (Symbol.iterator in Object(iter) || Object.prototype.toString.call(iter) === "[object Arguments]") return Array.from(iter); }

function _arrayWithoutHoles(arr) { if (Array.isArray(arr)) { for (var i = 0, arr2 = new Array(arr.length); i < arr.length; i++) { arr2[i] = arr[i]; } return arr2; } }

var getAllColumns = function getAllColumns(columns, parentColumn) {
  var result = [];
  columns.forEach(function (column) {
    column.parentId = parentColumn ? parentColumn.id : null;

    if (column.visible) {
      if (column.children && column.children.length && column.children.some(function (column) {
        return column.visible;
      })) {
        result.push(column);
        result.push.apply(result, _toConsumableArray(getAllColumns(column.children, column)));
      } else {
        result.push(column);
      }
    }
  });
  return result;
};

var convertToRows = function convertToRows(originColumns) {
  var maxLevel = 1;

  var traverse = function traverse(column, parent) {
    if (parent) {
      column.level = parent.level + 1;

      if (maxLevel < column.level) {
        maxLevel = column.level;
      }
    }

    if (column.children && column.children.length && column.children.some(function (column) {
      return column.visible;
    })) {
      var colSpan = 0;
      column.children.forEach(function (subColumn) {
        if (subColumn.visible) {
          traverse(subColumn, column);
          colSpan += subColumn.colSpan;
        }
      });
      column.colSpan = colSpan;
    } else {
      column.colSpan = 1;
    }
  };

  originColumns.forEach(function (column) {
    column.level = 1;
    traverse(column);
  });
  var rows = [];

  for (var i = 0; i < maxLevel; i++) {
    rows.push([]);
  }

  var allColumns = getAllColumns(originColumns);
  allColumns.forEach(function (column) {
    if (column.children && column.children.length && column.children.some(function (column) {
      return column.visible;
    })) {
      column.rowSpan = 1;
    } else {
      column.rowSpan = maxLevel - column.level + 1;
    }

    rows[column.level - 1].push(column);
  });
  return rows;
};

exports.convertToRows = convertToRows;