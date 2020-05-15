"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = exports.GlobalEvent = void 0;

var _xeUtils = _interopRequireDefault(require("xe-utils/methods/xe-utils"));

var _dom = _interopRequireDefault(require("./dom"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

// 监听全局事件
var browse = _dom.default.browse;
var wheelName = browse.firefox ? 'DOMMouseScroll' : 'mousewheel';
var eventStore = []; // 滚轮行为监听

var lastWheelTime;
var wheelEventTimeout;

function handleWheelEvent(evnt) {
  eventStore.forEach(function (_ref) {
    var comp = _ref.comp,
        type = _ref.type,
        cb = _ref.cb;

    if (type === 'syncwheel') {
      cb.call(comp, evnt);
    }
  });
  wheelEventTimeout = setTimeout(function () {
    if (lastWheelTime + 300 > Date.now()) {
      handleWheelEvent(evnt);
    } else {
      wheelEventTimeout = null;
    }
  }, 50);
}

function bindSyncwheelEvent(evnt) {
  if (!wheelEventTimeout) {
    handleWheelEvent(evnt);
  }

  lastWheelTime = Date.now();
}

var GlobalEvent = {
  on: function on(comp, type, cb) {
    if (cb) {
      eventStore.push({
        comp: comp,
        type: type,
        cb: cb
      });
    }
  },
  off: function off(comp, type) {
    _xeUtils.default.remove(eventStore, function (item) {
      return item.comp === comp && item.type === type;
    });
  },
  trigger: function trigger(evnt) {
    var isWheel = evnt.type === wheelName;
    eventStore.forEach(function (_ref2) {
      var comp = _ref2.comp,
          type = _ref2.type,
          cb = _ref2.cb;

      if (type === evnt.type || isWheel && type === 'mousewheel') {
        cb.call(comp, evnt);
      }
    });

    if (isWheel) {
      if (eventStore.some(function (_ref3) {
        var type = _ref3.type;
        return type === 'syncwheel';
      })) {
        bindSyncwheelEvent(evnt);
      }
    }
  }
};
exports.GlobalEvent = GlobalEvent;

if (browse.isDoc) {
  document.addEventListener('keydown', GlobalEvent.trigger, false);
  document.addEventListener('contextmenu', GlobalEvent.trigger, false);
  window.addEventListener('mousedown', GlobalEvent.trigger, false);
  window.addEventListener('blur', GlobalEvent.trigger, false);
  window.addEventListener('resize', GlobalEvent.trigger, false);
  window.addEventListener(wheelName, GlobalEvent.trigger, false);
}

var _default = GlobalEvent;
exports.default = _default;