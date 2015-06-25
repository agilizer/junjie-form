function power(t, e) {
    for (var n = t; e-- > 1; )
        n *= t;
    return n
}

function computeLoan(t) {
    var e = .12, n = .129, i = .129, s = e / 12, r = n / 12, o = i / 12, a = t, u = [];
    return C1 = a * s * power(1 + s, 12) / (power(1 + s, 12) - 1), C2 = a * r * power(1 + r, 24) / (power(1 + r, 24) - 1), C3 = a * o * power(1 + o, 36) / (power(1 + o, 36) - 1), u.push(C1.toFixed(0)), u.push(C2.toFixed(0)), u.push(C3.toFixed(0)), u
}
! function(t) {
    var e = "createTouch" in document || "ontouchstart" in t || 0, n = document.documentElement || document.getElementsByTagName("html")[0], i = "WebkitTransition" in n.style || "MsTransition" in n.style || "MozTransition" in n.style || "OTransition" in n.style || "transition" in n.style || 0, s = e ? "touchstart" : "mousedown", r = e ? "touchmove" : "mousemove", o = e ? "touchend" : "mouseup", a = function(t) {
        this.opt = this.parse_args(t), this.container = this.$(this.opt.id);
        try {
            if ("ul" == this.container.nodeName.toLowerCase() ? (this.element = this.container, this.container = this.element.parentNode) : this.element = this.container.getElementsByTagName("ul")[0], "undefined" == typeof this.element)
                throw new Error('Can\'t find "ul"');
            for (var e = 0; e < this.instance.length; e++)
                if (this.instance[e] == this.container)
                    throw new Error("An instance is running");
            this.instance.push(this.container), this.setup()
        } catch(n) {
            this.status = -1, this.errorInfo = n.message
        }
    };
    a.prototype = {
        _default : {
            id : "slider",
            fx : "ease-out",
            auto : 0,
            speed : 600,
            timeout : 5e3,
            className : "",
            direction : "left",
            mouseWheel : !1,
            before : new Function,
            after : new Function
        },
        instance : [],
        $ : function(t) {
            return document.getElementById(t)
        },
        $E : function(t, e, n) {
            for (var i = [], s = n.getElementsByTagName(e), r = 0, o = s.length; o > r; r++)
                new RegExp("(?:^|\\s+)" + t + "(?:\\s+|$)").test(s[r].className) && i.push(s[r]);
            return i
        },
        isIE : function() {
            return !-[1]
        },
        css : function() {
            var t = function(t) {
                switch(t) {
                    case"float":
                        return "cssFloat" in document.body.style ? "cssFloat" : "styleFloat";
                    case"opacity":
                        return "opacity" in document.body.style ? "opacity" : {
                            get : function(t, e) {
                                var n = e.filter;
                                return n && n.indexOf("opacity") >= 0 && parseFloat(n.match(/opacity=([^)]*)/i)[1]) / 100 + "" || "1"
                            },
                            set : function(t, e) {
                                t.style.filter = "alpha(opacity=" + 100 * e + ")", t.style.zoom = 1
                            }
                        };
                    default:
                        for (var e = t.split("-"), n = 1; n < e.length; n++)
                            e[n] = e[n].substring(0, 1).toUpperCase() + e[n].substring(1);
                        return t = e.join("")
                }
            }, e = function(e, n) {
                n = t(n);
                var i = e.style[n];
                if (!i) {
                    var s = document.defaultView && document.defaultView.getComputedStyle && getComputedStyle(e, null) || e.currentStyle || e.style;
                    i = "string" == typeof n ? s[n] : n.get(e, s)
                }
                return "auto" == i ? "" : i
            }, n = function(e, n) {
                var i;
                for (var s in n) i = t(s), "string" == typeof i ? e.style[i] = n[s] : i.set(e, n[s])
            };
            return function(t, i) {
                return "string" == typeof i ? e(t, i) : n(t, i)
            }
        }(),
        parse_args : function(t) {
            var e = {}, n = Object.prototype.toString;
            if (t && "[object Object]" == n.call(t))
                for (var i in this._default)
                e[i] = "undefined" == typeof t[i] ? this._default[i] : "[object Number]" == n.call(this._default[i]) ? parseInt(100 * parseFloat(t[i])) / 100 : t[i];
            else
                e = this._default;
            return e
        },
        addListener : function(t, e, n, i) {
            return t.addEventListener ? (t.addEventListener(e, n, i), !0) : t.attachEvent ? (t.attachEvent("on" + e, n), !0) : !1
        },
        getMousePoint : function(n) {
            var i = y = 0, s = document.documentElement, r = document.body;
            if (n || ( n = t.event), t.pageYoffset ? ( i = t.pageXOffset, y = t.pageYOffset) : ( i = (s && s.scrollLeft || r && r.scrollLeft || 0) - (s && s.clientLeft || r && r.clientLeft || 0), y = (s && s.scrollTop || r && r.scrollTop || 0) - (s && s.clientTop || r && r.clientTop || 0)), e && n.touches.length) {
                var o = n.touches[0];
                i += o.clientX, y += o.clientY
            } else
                i += n.clientX, y += n.clientY;
            return {
                x : i,
                y : y
            }
        },
        bind : function(t, e) {
            return function() {
                return t.apply(e, arguments)
            }
        },
        preventDefault : function(e) {
            t.event ? t.event.returnValue = !1 : e.preventDefault()
        },
        setup : function() {
            if (this.status = 0, this.slides = this.opt.className ? this.$E(this.opt.className, "li", this.element) : this.element.getElementsByTagName("li"), this.length = this.slides.length, this.opt.timeout = Math.max(this.opt.timeout, this.opt.speed), this.touching = !!e, this.css3transition = !!i, this.index = this.opt.auto < 0 || this.opt.auto >= this.length ? 0 : this.opt.auto, !(this.length < 2)) {
                switch(this.opt.direction) {
                    case"up":
                        this.direction = "up", this.vertical = !0;
                        break;
                    case"down":
                        this.direction = "down", this.vertical = !0;
                        break;
                    case"right":
                        this.direction = "right", this.vertical = !1;
                        break;
                    default:
                        this.direction = "left", this.vertical = !1
                }
                this.resize(), this.begin(), this.addListener(this.element, s, this.bind(this._start, this), !1), this.addListener(document, r, this.bind(this._move, this), !1), this.addListener(document, o, this.bind(this._end, this), !1), this.addListener(this.element, "webkitTransitionEnd", this.bind(this._transitionend, this), !1), this.addListener(this.element, "msTransitionEnd", this.bind(this._transitionend, this), !1), this.addListener(this.element, "oTransitionEnd", this.bind(this._transitionend, this), !1), this.addListener(this.element, "transitionend", this.bind(this._transitionend, this), !1), this.addListener(t, "resize", this.bind(function() {
                    clearTimeout(this.resizeTimer), this.resizeTimer = setTimeout(this.bind(this.resize, this), 100)
                }, this), !1), this.addListener(this.element, "mousewheel", this.bind(this.mouseScroll, this), !1), this.addListener(this.element, "DOMMouseScroll", this.bind(this.mouseScroll, this), !1)
            }
        },
        resize : function() {
            var t;
            this.css(this.container, {
                overflow : "hidden",
                visibility : "hidden",
                listStyle : "none",
                position : "relative"
            }), this.width = this.container.clientWidth - parseInt(this.css(this.container, "padding-left")) - parseInt(this.css(this.container, "padding-right")), this.height = this.container.clientHeight - parseInt(this.css(this.container, "padding-top")) - parseInt(this.css(this.container, "padding-bottom")), t = {
                position : "relative",
                webkitTransitionDuration : "0ms",
                MozTransitionDuration : "0ms",
                msTransitionDuration : "0ms",
                OTransitionDuration : "0ms",
                transitionDuration : "0ms"
            }, this.vertical ? (t.height = this.height * this.length + "px", t.top = -this.height * this.index + "px", this.css(this.container, {
                height : this.height + "px"
            })) : (t.width = this.width * this.length + "px", t.left = -this.width * this.index + "px"), this.css(this.element, t);
            for (var e = 0; e < this.length; e++)
                this.css(this.slides[e], {
                    width : this.width + "px",
                    display : this.vertical ? "table-row" : "table-cell",
                    padding : 0,
                    margin : 0,
                    "float" : "left",
                    verticalAlign : "top"
                });
            this.css(this.container, {
                visibility : "visible"
            })
        },
        slide : function(t, e) {
            var n = this.vertical ? "top" : "left", s = this.vertical ? "height" : "width";
            t = 0 > t ? this.length - 1 : t >= this.length ? 0 : t, e = "undefined" == typeof e ? this.opt.speed : parseInt(e);
            var r = this.element, o = null, a = r.style, u = this, c = 0, l = parseInt(a[n]) || 0, h = -t * this[s] - l, f = Math.abs(h) < this[s] ? Math.ceil(Math.abs(h) / this[s] * e / 10) : e / 10, d = function(t, e, n, i) {
                return -n * (( t = t / i - 1) * t * t * t - 1) + e
            }, p = function() {
                f > c && !i ? (c++, a[n] = Math.ceil(d(c, l, h, f)) + "px", o = setTimeout(p, 10)) : (a[n] = -u[s] * t + "px", u.index = t, i || u._transitionend(), u.pause(), u.begin())
            };
            a.WebkitTransition = a.MozTransition = a.msTransition = a.OTransition = a.transition = n + " " + 10 * f + "ms " + this.opt.fx, this.opt.before.call(this, t, this.slides[this.index]), p()
        },
        begin : function() {
            return this.timer || this.opt.auto < 0 ? !0 : (this.timer = setTimeout(this.bind(function() {
                "left" == this.direction || "up" == this.direction ? this.next() : this.prev()
            }, this), this.opt.timeout),
            void (this.status = 1))
        },
        pause : function() {
            clearInterval(this.timer), this.timer = null, this.status = 2
        },
        stop : function() {
            this.pause(), this.index = 0, this.slide(0), this.status = 0
        },
        prev : function(t) {
            t = "undefined" == typeof t ? t = 1 : t % this.length;
            var e = t > this.index ? this.length + this.index - t : this.index - t;
            this.slide(e)
        },
        next : function(t) {
            "undefined" == typeof t && ( t = 1), this.slide((this.index + t) % this.length)
        },
        _start : function(t) {
            this.touching || this.preventDefault(t), this.element.onclick = null, this.startPos = this.getMousePoint(t);
            var e = this.element.style;
            e.webkitTransitionDuration = e.MozTransitionDuration = e.msTransitionDuration = e.OTransitionDuration = e.transitionDuration = "0ms", this.scrolling = 1, this.startTime = new Date
        },
        _move : function(t) {
            if (!(!this.scrolling || t.touches && t.touches.length > 1 || t.scale && 1 !== t.scale)) {
                var e = this.vertical ? "top" : "left", n = this.vertical ? "height" : "width", i = this.vertical ? "y" : "x", s = this.vertical ? "x" : "y";
                this.endPos = this.getMousePoint(t);
                var r = this.endPos[i] - this.startPos[i];
                2 === this.scrolling || Math.abs(r) >= Math.abs(this.endPos[s] - this.startPos[s]) ? (this.preventDefault(t), this.pause(), r /= !this.index && r > 0 || this.index == this.length - 1 && 0 > r ? Math.abs(r) / this[n] + 1 : 1, this.element.style[e] = -this.index * this[n] + r + "px", 0 != r && (this.scrolling = 2)) : this.scrolling = 0
            }
        },
        _end : function() {
            if ("undefined" != typeof this.scrolling) {
                try {
                    var t = this.vertical ? "y" : "x", e = this.vertical ? "height" : "width", n = this.endPos[t] - this.startPos[t];
                    2 === this.scrolling && (this.element.onclick = new Function("return false;"))
                } catch(i) {
                    n = 0
                }
                (new Date - this.startTime < 250 && Math.abs(n) > .1 * this[e] || Math.abs(n) > this[e] / 2) && (0 > n && this.index + 1 < this.length || n > 0 && this.index > 0) ? n > 0 ? this.prev() : this.next() : this.slide(this.index),
                delete this.scrolling,
                delete this.startPos,
                delete this.endPos,
                delete this.startTime, this.opt.auto >= 0 && this.begin()
            }
        },
        mouseScroll : function(e) {
            if (this.opt.mouseWheel) {
                this.preventDefault(e), e = e || t.event;
                {
                    var n = e.wheelDelta || e.detail && -1 * e.detail || 0;
                    n / Math.abs(n)
                }
                n > 0 ? this.next() : this.prev()
            }
        },
        _transitionend : function() {
            this.opt.after.call(this, this.index, this.slides[this.index])
        }
    }, t.TouchSlider = a
}(window,
void 0), $(function() {
    var t = $(".nav-container"), e = $("nav"), n = 0, i = 50;
    t.waypoint({
        handler : function(s, r) {
            "down" == r ? (t.css({
                height : e.outerHeight()
            }), e.stop().addClass("sticky").css("top", -e.outerHeight()).animate({
                top : n
            })) : (t.css({
                height : "auto"
            }), e.stop().removeClass("sticky").css("top", e.outerHeight() + i).animate({
                top : ""
            }))
        },
        offset : function() {
            return -e.outerHeight() - i
        }
    });
    var s = $("section"), r = $("nav a");
    s.waypoint({
        handler : function(t, e) {
            var n;
            n = $(this), "up" === e && ( n = n.prev());
            var i = $('nav a[href="#' + n.attr("id") + '"]');
            r.removeClass("selected"), i.addClass("selected")
        },
        offset : "25%"
    }), r.click(function() {
        $.scrollTo($(this).attr("href"), {
            duration : 200,
            offset : {
                left : 0,
                top : -.15 * $(window).height()
            }
        })
    })
}), $(document).ready(function() {
    $(".select_box1").click(function(t) {
        t.stopPropagation(), $(this).find(".option1").toggle(), $(this).parent().siblings().find(".option1").hide()
    }), $(document).click(function(t) {
        var e = $(t.target);
        $(".select_box1").is(":visible") && "option1" != e.attr("class") && !e.parent(".option1").length && $(".option1").hide()
    }), $(".option1 a").click(function() {
        var t = $(this).text();
        $(this).parent().siblings(".select_txt1").text(t)
    })
}), function(t) {
    String.prototype.trim === t && (String.prototype.trim = function() {
        return this.replace(/^\s+/, "").replace(/\s+$/, "")
    }), Array.prototype.reduce === t && (Array.prototype.reduce = function(e) {
        if (
            void 0 === this || null === this)
            throw new TypeError;
        var n, i = Object(this), s = i.length >>> 0, r = 0;
        if ("function" != typeof e)
            throw new TypeError;
        if (0 == s && 1 == arguments.length)
            throw new TypeError;
        if (arguments.length >= 2)
            n = arguments[1];
        else
            for (; ; ) {
                if ( r in i) {
                    n = i[r++];
                    break
                }
                if (++r >= s)
                    throw new TypeError
            }
        for (; s > r; )
            r in i && ( n = e.call(t, n, i[r], r, i)), r++;
        return n
    })
}();
var Zepto = function() {
    function t(t) {
        return "[object Function]" == R.call(t)
    }

    function e(t) {
        return t instanceof Object
    }

    function n(e) {
        var n, i;
        if ("[object Object]" !== R.call(e))
            return !1;
        if ( i = t(e.constructor) && e.constructor.prototype, !i || !hasOwnProperty.call(i, "isPrototypeOf"))
            return !1;
        for (n in e);
        return n === m || hasOwnProperty.call(e, n)
    }

    function i(t) {
        return t instanceof Array
    }

    function s(t) {
        return "number" == typeof t.length
    }

    function r(t) {
        return t.filter(function(t) {
            return t !== m && null !== t
        })
    }

    function o(t) {
        return t.length > 0 ? [].concat.apply([], t) : t
    }

    function a(t) {
        return t.replace(/::/g, "/").replace(/([A-Z]+)([A-Z][a-z])/g, "$1_$2").replace(/([a-z\d])([A-Z])/g, "$1_$2").replace(/_/g, "-").toLowerCase()
    }

    function u(t) {
        return t in j ? j[t] : j[t] = new RegExp("(^|\\s)" + t + "(\\s|$)")
    }

    function c(t, e) {
        return "number" != typeof e || O[a(t)] ? e : e + "px"
    }

    function l(t) {
        var e, n;
        return S[t] || ( e = E.createElement(t), E.body.appendChild(e), n = C(e, "").getPropertyValue("display"), e.parentNode.removeChild(e), "none" == n && ( n = "block"), S[t] = n), S[t]
    }

    function h(t, e) {
        return e === m ? v(t) : v(t).filter(e)
    }

    function f(e, n, i, s) {
        return t(n) ? n.call(e, i, s) : n
    }

    function d(t, e, n) {
        var i = t % 2 ? e : e.parentNode;
        i ? i.insertBefore(n, t ? 1 == t ? i.firstChild : 2 == t ? e : null : e.nextSibling) : v(n).remove()
    }

    function p(t, e) {
        e(t);
        for (var n in t.childNodes)p(t.childNodes[n], e)
    }

    var m, g, v, y, w, x, b = [], T = b.slice, E = window.document, S = {}, j = {}, C = E.defaultView.getComputedStyle, O = {
        "column-count" : 1,
        columns : 1,
        "font-weight" : 1,
        "line-height" : 1,
        opacity : 1,
        "z-index" : 1,
        zoom : 1
    }, N = /^\s*<(\w+|!)[^>]*>/, $ = [1, 3, 8, 9, 11], P = ["after", "prepend", "before", "append"], k = E.createElement("table"), M = E.createElement("tr"), _ = {
        tr : E.createElement("tbody"),
        tbody : k,
        thead : k,
        tfoot : k,
        td : M,
        th : M,
        "*" : E.createElement("div")
    }, L = /complete|loaded|interactive/, z = /^\.([\w-]+)$/, D = /^#([\w-]+)$/, A = /^[\w-]+$/, R = {}.toString, H = {}, Z = E.createElement("div");
    return H.matches = function(t, e) {
        if (!t || 1 !== t.nodeType)
            return !1;
        var n = t.webkitMatchesSelector || t.mozMatchesSelector || t.oMatchesSelector || t.matchesSelector;
        if (n)
            return n.call(t, e);
        var i, s = t.parentNode, r = !s;
        return r && ( s = Z).appendChild(t), i = ~H.qsa(s, e).indexOf(t), r && Z.removeChild(t), i
    }, w = function(t) {
        return t.replace(/-+(.)?/g, function(t, e) {
            return e ? e.toUpperCase() : ""
        })
    }, x = function(t) {
        return t.filter(function(e, n) {
            return t.indexOf(e) == n
        })
    }, H.fragment = function(t, e) {
        e === m && ( e = N.test(t) && RegExp.$1), e in _ || ( e = "*");
        var n = _[e];
        return n.innerHTML = "" + t, v.each(T.call(n.childNodes), function() {
            n.removeChild(this)
        })
    }, H.Z = function(t, e) {
        return t = t || [], t.__proto__ = arguments.callee.prototype, t.selector = e || "", t
    }, H.isZ = function(t) {
        return t instanceof H.Z
    }, H.init = function(e, s) {
        if (!e)
            return H.Z();
        if (t(e))
            return v(E).ready(e);
        if (H.isZ(e))
            return e;
        var o;
        if (i(e))
            o = r(e);
        else if (n(e))
            o = [v.extend({}, e)], e = null;
        else if ($.indexOf(e.nodeType) >= 0 || e === window)
            o = [e], e = null;
        else if (N.test(e))
            o = H.fragment(e.trim(), RegExp.$1), e = null;
        else {
            if (s !== m)
                return v(s).find(e);
            o = H.qsa(E, e)
        }
        return H.Z(o, e)
    }, v = function(t, e) {
        return H.init(t, e)
    }, v.extend = function(t) {
        return T.call(arguments, 1).forEach(function(e) {
            for (g in e)e[g] !== m && (t[g] = e[g])
        }), t
    }, H.qsa = function(t, e) {
        var n;
        return t === E && D.test(e) ? ( n = t.getElementById(RegExp.$1)) ? [n] : b : 1 !== t.nodeType && 9 !== t.nodeType ? b : T.call(z.test(e) ? t.getElementsByClassName(RegExp.$1) : A.test(e) ? t.getElementsByTagName(e) : t.querySelectorAll(e))
    }, v.isFunction = t, v.isObject = e, v.isArray = i, v.isPlainObject = n, v.inArray = function(t, e, n) {
        return b.indexOf.call(e, t, n)
    }, v.trim = function(t) {
        return t.trim()
    }, v.uuid = 0, v.map = function(t, e) {
        var n, i, r, a = [];
        if (s(t))
            for ( i = 0; i < t.length; i++)
                n = e(t[i], i), null != n && a.push(n);
        else
            for (r in t) n = e(t[r], r), null != n && a.push(n);
        return o(a)
    }, v.each = function(t, e) {
        var n, i;
        if (s(t)) {
            for ( n = 0; n < t.length; n++)
                if (e.call(t[n], n, t[n]) === !1)
                    return t
        } else
            for (i in t)
            if (e.call(t[i], i, t[i]) === !1)
                return t;
        return t
    }, v.fn = {
        forEach : b.forEach,
        reduce : b.reduce,
        push : b.push,
        indexOf : b.indexOf,
        concat : b.concat,
        map : function(t) {
            return v.map(this, function(e, n) {
                return t.call(e, n, e)
            })
        },
        slice : function() {
            return v(T.apply(this, arguments))
        },
        ready : function(t) {
            return L.test(E.readyState) ? t(v) : E.addEventListener("DOMContentLoaded", function() {
                t(v)
            }, !1), this
        },
        get : function(t) {
            return t === m ? T.call(this) : this[t]
        },
        toArray : function() {
            return this.get()
        },
        size : function() {
            return this.length
        },
        remove : function() {
            return this.each(function() {
                null != this.parentNode && this.parentNode.removeChild(this)
            })
        },
        each : function(t) {
            return this.forEach(function(e, n) {
                t.call(e, n, e)
            }), this
        },
        filter : function(t) {
            return v([].filter.call(this, function(e) {
                return H.matches(e, t)
            }))
        },
        add : function(t, e) {
            return v(x(this.concat(v(t, e))))
        },
        is : function(t) {
            return this.length > 0 && H.matches(this[0], t)
        },
        not : function(e) {
            var n = [];
            if (t(e) && e.call !== m)
                this.each(function(t) {
                    e.call(this, t) || n.push(this)
                });
            else {
                var i = "string" == typeof e ? this.filter(e) : s(e) && t(e.item) ? T.call(e) : v(e);
                this.forEach(function(t) {
                    i.indexOf(t) < 0 && n.push(t)
                })
            }
            return v(n)
        },
        eq : function(t) {
            return -1 === t ? this.slice(t) : this.slice(t, +t + 1)
        },
        first : function() {
            var t = this[0];
            return t && !e(t) ? t : v(t)
        },
        last : function() {
            var t = this[this.length - 1];
            return t && !e(t) ? t : v(t)
        },
        find : function(t) {
            var e;
            return e = 1 == this.length ? H.qsa(this[0], t) : this.map(function() {
                return H.qsa(this, t)
            }), v(e)
        },
        closest : function(t, e) {
            for (var n = this[0]; n && !H.matches(n, t); )
                n = n !== e && n !== E && n.parentNode;
            return v(n)
        },
        parents : function(t) {
            for (var e = [], n = this; n.length > 0; )
                n = v.map(n, function(t) {
                    return ( t = t.parentNode) && t !== E && e.indexOf(t) < 0 ? (e.push(t), t) :
                    void 0
                });
            return h(e, t)
        },
        parent : function(t) {
            return h(x(this.pluck("parentNode")), t)
        },
        children : function(t) {
            return h(this.map(function() {
                return T.call(this.children)
            }), t)
        },
        siblings : function(t) {
            return h(this.map(function(t, e) {
                return T.call(e.parentNode.children).filter(function(t) {
                    return t !== e
                })
            }), t)
        },
        empty : function() {
            return this.each(function() {
                this.innerHTML = ""
            })
        },
        pluck : function(t) {
            return this.map(function() {
                return this[t]
            })
        },
        show : function() {
            return this.each(function() {
                "none" == this.style.display && (this.style.display = null), "none" == C(this, "").getPropertyValue("display") && (this.style.display = l(this.nodeName))
            })
        },
        replaceWith : function(t) {
            return this.before(t).remove()
        },
        wrap : function(t) {
            return this.each(function() {
                v(this).wrapAll(v(t)[0].cloneNode(!1))
            })
        },
        wrapAll : function(t) {
            return this[0] && (v(this[0]).before( t = v(t)), t.append(this)), this
        },
        unwrap : function() {
            return this.parent().each(function() {
                v(this).replaceWith(v(this).children())
            }), this
        },
        clone : function() {
            return v(this.map(function() {
                return this.cloneNode(!0)
            }))
        },
        hide : function() {
            return this.css("display", "none")
        },
        toggle : function(t) {
            return (t === m ? "none" == this.css("display") : t) ? this.show() : this.hide()
        },
        prev : function() {
            return v(this.pluck("previousElementSibling"))
        },
        next : function() {
            return v(this.pluck("nextElementSibling"))
        },
        html : function(t) {
            return t === m ? this.length > 0 ? this[0].innerHTML : null : this.each(function(e) {
                var n = this.innerHTML;
                v(this).empty().append(f(this, t, e, n))
            })
        },
        text : function(t) {
            return t === m ? this.length > 0 ? this[0].textContent : null : this.each(function() {
                this.textContent = t
            })
        },
        attr : function(t, n) {
            var i;
            return "string" == typeof t && n === m ? 0 == this.length || 1 !== this[0].nodeType ? m : "value" == t && "INPUT" == this[0].nodeName ? this.val() : !( i = this[0].getAttribute(t)) && t in this[0] ? this[0][t] : i : this.each(function(i) {
                if (1 === this.nodeType)
                    if (e(t))
                        for (g in t)
                        this.setAttribute(g, t[g]);
                    else
                        this.setAttribute(t, f(this, n, i, this.getAttribute(t)))
            })
        },
        removeAttr : function(t) {
            return this.each(function() {
                1 === this.nodeType && this.removeAttribute(t)
            })
        },
        prop : function(t, e) {
            return e === m ? this[0] ? this[0][t] : m : this.each(function(n) {
                this[t] = f(this, e, n, this[t])
            })
        },
        data : function(t, e) {
            var n = this.attr("data-" + a(t), e);
            return null !== n ? n : m
        },
        val : function(t) {
            return t === m ? this.length > 0 ? this[0].value : m : this.each(function(e) {
                this.value = f(this, t, e, this.value)
            })
        },
        offset : function() {
            if (0 == this.length)
                return null;
            var t = this[0].getBoundingClientRect();
            return {
                left : t.left + window.pageXOffset,
                top : t.top + window.pageYOffset,
                width : t.width,
                height : t.height
            }
        },
        css : function(t, e) {
            if (e === m && "string" == typeof t)
                return 0 == this.length ? m : this[0].style[w(t)] || C(this[0], "").getPropertyValue(t);
            var n = "";
            for (g in t)"string" == typeof t[g] && "" == t[g] ? this.each(function() {
                this.style.removeProperty(a(g))
            }) : n += a(g) + ":" + c(g, t[g]) + ";";
            return "string" == typeof t && ("" == e ? this.each(function() {
                this.style.removeProperty(a(t))
            }) : n = a(t) + ":" + c(t, e)), this.each(function() {
                this.style.cssText += ";" + n
            })
        },
        index : function(t) {
            return t ? this.indexOf(v(t)[0]) : this.parent().children().indexOf(this[0])
        },
        hasClass : function(t) {
            return this.length < 1 ? !1 : u(t).test(this[0].className)
        },
        addClass : function(t) {
            return this.each(function(e) {
                y = [];
                var n = this.className, i = f(this, t, e, n);
                i.split(/\s+/g).forEach(function(t) {
                    v(this).hasClass(t) || y.push(t)
                }, this), y.length && (this.className += ( n ? " " : "") + y.join(" "))
            })
        },
        removeClass : function(t) {
            return this.each(function(e) {
                return t === m ? this.className = "" : ( y = this.className, f(this, t, e, y).split(/\s+/g).forEach(function(t) {
                    y = y.replace(u(t), " ")
                }), this.className = y.trim(),
                void 0)
            })
        },
        toggleClass : function(t, e) {
            return this.each(function(n) {
                var i = f(this, t, n, this.className);
                (e === m ? !v(this).hasClass(i) : e) ? v(this).addClass(i) : v(this).removeClass(i)
            })
        }
    }, ["width", "height"].forEach(function(t) {
        v.fn[t] = function(e) {
            var n, i = t.replace(/./, function(t) {
                return t[0].toUpperCase()
            });
            return e === m ? this[0] == window ? window["inner" + i] : this[0] == E ? E.documentElement["offset" + i] : ( n = this.offset()) && n[t] : this.each(function(n) {
                var i = v(this);
                i.css(t, f(this, e, n, i[t]()))
            })
        }
    }), P.forEach(function(t, n) {
        v.fn[t] = function() {
            var t = v.map(arguments, function(t) {
                return e(t) ? t : H.fragment(t)
            });
            if (t.length < 1)
                return this;
            var i = this.length, s = i > 1, r = 2 > n;
            return this.each(function(e, o) {
                for (var a = 0; a < t.length; a++) {
                    var u = t[ r ? t.length - a - 1 : a];
                    p(u, function(t) {
                        null != t.nodeName && "SCRIPT" === t.nodeName.toUpperCase() && (!t.type || "text/javascript" === t.type) && window.eval.call(window, t.innerHTML)
                    }), s && i - 1 > e && ( u = u.cloneNode(!0)), d(n, o, u)
                }
            })
        }, v.fn[n % 2 ? t + "To" : "insert" + ( n ? "Before" : "After")] = function(e) {
            return v(e)[t](this), this
        }
    }), H.Z.prototype = v.fn, H.camelize = w, H.uniq = x, v.zepto = H, v
}();
window.Zepto = Zepto, "$" in window || (window.$ = Zepto), function(t) {
    function e(t) {
        return t._zid || (t._zid = h++)
    }

    function n(t, n, r, o) {
        if ( n = i(n), n.ns)
            var a = s(n.ns);
        return (l[e(t)] || []).filter(function(t) {
            return !(!t || n.e && t.e != n.e || n.ns && !a.test(t.ns) || r && e(t.fn) !== e(r) || o && t.sel != o)
        })
    }

    function i(t) {
        var e = ("" + t).split(".");
        return {
            e : e[0],
            ns : e.slice(1).sort().join(" ")
        }
    }

    function s(t) {
        return new RegExp("(?:^| )" + t.replace(" ", " .* ?") + "(?: |$)")
    }

    function r(e, n, i) {
        t.isObject(e) ? t.each(e, i) : e.split(/\s/).forEach(function(t) {
            i(t, n)
        })
    }

    function o(n, s, o, a, u, c) {
        c = !!c;
        var h = e(n), f = l[h] || (l[h] = []);
        r(s, o, function(e, s) {
            var r = u && u(s, e), o = r || s, l = function(t) {
                var e = o.apply(n, [t].concat(t.data));
                return e === !1 && t.preventDefault(), e
            }, h = t.extend(i(e), {
                fn : s,
                proxy : l,
                sel : a,
                del : r,
                i : f.length
            });
            f.push(h), n.addEventListener(h.e, l, c)
        })
    }

    function a(t, i, s, o) {
        var a = e(t);
        r(i || "", s, function(e, i) {
            n(t, e, i, o).forEach(function(e) {
                delete l[a][e.i], t.removeEventListener(e.e, e.proxy, !1)
            })
        })
    }

    function u(e) {
        var n = t.extend({
            originalEvent : e
        }, e);
        return t.each(m, function(t, i) {
            n[t] = function() {
                return this[i] = d, e[t].apply(e, arguments)
            }, n[i] = p
        }), n
    }

    function c(t) {
        if (!("defaultPrevented" in t)) {
            t.defaultPrevented = !1;
            var e = t.preventDefault;
            t.preventDefault = function() {
                this.defaultPrevented = !0, e.call(this)
            }
        }
    }

    var l = (t.zepto.qsa, {}), h = 1, f = {};
    f.click = f.mousedown = f.mouseup = f.mousemove = "MouseEvents", t.event = {
        add : o,
        remove : a
    }, t.proxy = function(n, i) {
        if (t.isFunction(n)) {
            var s = function() {
                return n.apply(i, arguments)
            };
            return s._zid = e(n), s
        }
        if ("string" == typeof i)
            return t.proxy(n[i], n);
        throw new TypeError("expected function")
    }, t.fn.bind = function(t, e) {
        return this.each(function() {
            o(this, t, e)
        })
    }, t.fn.unbind = function(t, e) {
        return this.each(function() {
            a(this, t, e)
        })
    }, t.fn.one = function(t, e) {
        return this.each(function(n, i) {
            o(this, t, e, null, function(t, e) {
                return function() {
                    var n = t.apply(i, arguments);
                    return a(i, e, t), n
                }
            })
        })
    };
    var d = function() {
        return !0
    }, p = function() {
        return !1
    }, m = {
        preventDefault : "isDefaultPrevented",
        stopImmediatePropagation : "isImmediatePropagationStopped",
        stopPropagation : "isPropagationStopped"
    };
    t.fn.delegate = function(e, n, i) {
        var s = !1;
        return ("blur" == n || "focus" == n) && (t.iswebkit ? n = "blur" == n ? "focusout" : "focus" == n ? "focusin" : n : s = !0), this.each(function(r, a) {
            o(a, n, i, e, function(n) {
                return function(i) {
                    var s, r = t(i.target).closest(e, a).get(0);
                    return r ? ( s = t.extend(u(i), {
                        currentTarget : r,
                        liveFired : a
                    }), n.apply(r, [s].concat([].slice.call(arguments, 1)))) :
                    void 0
                }
            }, s)
        })
    }, t.fn.undelegate = function(t, e, n) {
        return this.each(function() {
            a(this, e, n, t)
        })
    }, t.fn.live = function(e, n) {
        return t(document.body).delegate(this.selector, e, n), this
    }, t.fn.die = function(e, n) {
        return t(document.body).undelegate(this.selector, e, n), this
    }, t.fn.on = function(e, n, i) {
        return
        void 0 == n || t.isFunction(n) ? this.bind(e, n) : this.delegate(n, e, i)
    }, t.fn.off = function(e, n, i) {
        return
        void 0 == n || t.isFunction(n) ? this.unbind(e, n) : this.undelegate(n, e, i)
    }, t.fn.trigger = function(e, n) {
        return "string" == typeof e && ( e = t.Event(e)), c(e), e.data = n, this.each(function() {
            "dispatchEvent" in this && this.dispatchEvent(e)
        })
    }, t.fn.triggerHandler = function(e, i) {
        var s, r;
        return this.each(function(o, a) {
            s = u("string" == typeof e ? t.Event(e) : e), s.data = i, s.target = a, t.each(n(a, e.type || e), function(t, e) {
                return r = e.proxy(s), s.isImmediatePropagationStopped() ? !1 :
                void 0
            })
        }), r
    }, "focusin focusout load resize scroll unload click dblclick mousedown mouseup mousemove mouseover mouseout change select keydown keypress keyup error".split(" ").forEach(function(e) {
        t.fn[e] = function(t) {
            return this.bind(e, t)
        }
    }), ["focus", "blur"].forEach(function(e) {
        t.fn[e] = function(t) {
            if (t)
                this.bind(e, t);
            else if (this.length)
                try {
                    this.get(0)[e]()
                } catch(n) {
                }
            return this
        }
    }), t.Event = function(t, e) {
        var n = document.createEvent(f[t] || "Events"), i = !0;
        if (e)
            for (var s in e)"bubbles" == s ? i = !!e[s] : n[s] = e[s];
        return n.initEvent(t, i, !0, null, null, null, null, null, null, null, null, null, null, null, null), n
    }
}(Zepto), function(t) {
    function e(t) {
        var e = this.os = {}, n = this.browser = {}, i = t.match(/WebKit\/([\d.]+)/), s = t.match(/(Android)\s+([\d.]+)/), r = t.match(/(iPad).*OS\s([\d_]+)/), o = !r && t.match(/(iPhone\sOS)\s([\d_]+)/), a = t.match(/(webOS|hpwOS)[\s\/]([\d.]+)/), u = a && t.match(/TouchPad/), c = t.match(/Kindle\/([\d.]+)/), l = t.match(/Silk\/([\d._]+)/), h = t.match(/(BlackBerry).*Version\/([\d.]+)/);
        (n.webkit = !!i) && (n.version = i[1]), s && (e.android = !0, e.version = s[2]), o && (e.ios = e.iphone = !0, e.version = o[2].replace(/_/g, ".")), r && (e.ios = e.ipad = !0, e.version = r[2].replace(/_/g, ".")), a && (e.webos = !0, e.version = a[2]), u && (e.touchpad = !0), h && (e.blackberry = !0, e.version = h[2]), c && (e.kindle = !0, e.version = c[1]), l && (n.silk = !0, n.version = l[1]), !l && e.android && t.match(/Kindle Fire/) && (n.silk = !0)
    }
    e.call(t, navigator.userAgent), t.__detect = e
}(Zepto), function(t, e) {
    function n(t) {
        return t.toLowerCase()
    }

    function i(t) {
        return s ? s + t : n(t)
    }

    var s, r = "", o = {
        Webkit : "webkit",
        Moz : "",
        O : "o",
        ms : "MS"
    }, a = window.document, u = a.createElement("div"), c = /^((translate|rotate|scale)(X|Y|Z|3d)?|matrix(3d)?|perspective|skew(X|Y)?)$/i, l = {};
    t.each(o, function(t, i) {
        return u.style[t + "TransitionProperty"] !== e ? ( r = "-" + n(t) + "-", s = i, !1) :
        void 0
    }), l[r + "transition-property"] = l[r + "transition-duration"] = l[r + "transition-timing-function"] = l[r + "animation-name"] = l[r + "animation-duration"] = "", t.fx = {
        off : s === e && u.style.transitionProperty === e,
        cssPrefix : r,
        transitionEnd : i("TransitionEnd"),
        animationEnd : i("AnimationEnd")
    }, t.fn.animate = function(e, n, i, s) {
        return t.isObject(n) && ( i = n.easing, s = n.complete, n = n.duration), n && (n /= 1e3), this.anim(e, n, i, s)
    }, t.fn.anim = function(n, i, s, o) {
        var a, u, h, f = {}, d = this, p = t.fx.transitionEnd;
        if (i === e && ( i = .4), t.fx.off && ( i = 0), "string" == typeof n)
            f[r + "animation-name"] = n, f[r + "animation-duration"] = i + "s", p = t.fx.animationEnd;
        else {
            for (u in n)c.test(u) ? (a || ( a = []), a.push(u + "(" + n[u] + ")")) : f[u] = n[u];
            a && (f[r + "transform"] = a.join(" ")), !t.fx.off && "object" == typeof n && (f[r + "transition-property"] = Object.keys(n).join(", "), f[r + "transition-duration"] = i + "s", f[r + "transition-timing-function"] = s || "linear")
        }
        return h = function(e) {
            if ("undefined" != typeof e) {
                if (e.target !== e.currentTarget)
                    return;
                t(e.target).unbind(p, arguments.callee)
            }
            t(this).css(l), o && o.call(this)
        }, i > 0 && this.bind(p, h), setTimeout(function() {
            d.css(f), 0 >= i && setTimeout(function() {
                d.each(function() {
                    h.call(this)
                })
            }, 0)
        }, 0), this
    }, u = null
}(Zepto), function(t) {
    function e(e, n, i) {
        var s = t.Event(n);
        return t(e).trigger(s, i), !s.defaultPrevented
    }

    function n(t, n, i, s) {
        return t.global ? e(n || y, i, s) :
        void 0
    }

    function i(e) {
        e.global && 0 === t.active++ && n(e, null, "ajaxStart")
    }

    function s(e) {
        e.global && !--t.active && n(e, null, "ajaxStop")
    }

    function r(t, e) {
        var i = e.context;
        return e.beforeSend.call(i, t, e) === !1 || n(e, i, "ajaxBeforeSend", [t, e]) === !1 ? !1 :
        void  n(e, i, "ajaxSend", [t, e])
    }

    function o(t, e, i) {
        var s = i.context, r = "success";
        i.success.call(s, t, r, e), n(i, s, "ajaxSuccess", [e, i, t]), u(r, e, i)
    }

    function a(t, e, i, s) {
        var r = s.context;
        s.error.call(r, i, e, t), n(s, r, "ajaxError", [i, s, t]), u(e, i, s)
    }

    function u(t, e, i) {
        var r = i.context;
        i.complete.call(r, e, t), n(i, r, "ajaxComplete", [e, i]), s(i)
    }

    function c() {
    }

    function l(t) {
        return t && (t == E ? "html" : t == T ? "json" : x.test(t) ? "script" : b.test(t) && "xml") || "text"
    }

    function h(t, e) {
        return (t + "&" + e).replace(/[&?]{1,2}/, "?")
    }

    function f(e) {
        v(e.data) && (e.data = t.param(e.data)), e.data && (!e.type || "GET" == e.type.toUpperCase()) && (e.url = h(e.url, e.data))
    }

    function d(e, n, i, s) {
        var r = t.isArray(n);
        t.each(n, function(n, o) {
            s && ( n = i ? s : s + "[" + ( r ? "" : n) + "]"), !s && r ? e.add(o.name, o.value) : ( i ? t.isArray(o) : v(o)) ? d(e, o, i, n) : e.add(n, o)
        })
    }

    var p, m, g = 0, v = t.isObject, y = window.document, w = /<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi, x = /^(?:text|application)\/javascript/i, b = /^(?:text|application)\/xml/i, T = "application/json", E = "text/html", S = /^\s*$/;
    t.active = 0, t.ajaxJSONP = function(e) {
        var n, i = "jsonp" + ++g, s = y.createElement("script"), r = function() {
            t(s).remove(), i in window && (window[i] = c), u("abort", a, e)
        }, a = {
            abort : r
        };
        return e.error && (s.onerror = function() {
            a.abort(), e.error()
        }), window[i] = function(r) {
            clearTimeout(n), t(s).remove(),
            delete window[i], o(r, a, e)
        }, f(e), s.src = e.url.replace(/=\?/, "=" + i), t("head").append(s), e.timeout > 0 && ( n = setTimeout(function() {
            a.abort(), u("timeout", a, e)
        }, e.timeout)), a
    }, t.ajaxSettings = {
        type : "GET",
        beforeSend : c,
        success : c,
        error : c,
        complete : c,
        context : null,
        global : !0,
        xhr : function() {
            return new window.XMLHttpRequest
        },
        accepts : {
            script : "text/javascript, application/javascript",
            json : T,
            xml : "application/xml, text/xml",
            html : E,
            text : "text/plain"
        },
        crossDomain : !1,
        timeout : 0
    }, t.ajax = function(e) {
        var n = t.extend({}, e || {});
        for (p in t.ajaxSettings)
        void 0 === n[p] && (n[p] = t.ajaxSettings[p]);
        i(n), n.crossDomain || (n.crossDomain = /^([\w-]+:)?\/\/([^\/]+)/.test(n.url) && RegExp.$2 != window.location.host);
        var s = n.dataType, u = /=\?/.test(n.url);
        if ("jsonp" == s || u)
            return u || (n.url = h(n.url, "callback=?")), t.ajaxJSONP(n);
        n.url || (n.url = window.location.toString()), f(n);
        var d, g = n.accepts[s], v = {}, y = /^([\w-]+:)\/\//.test(n.url) ? RegExp.$1 : window.location.protocol, w = t.ajaxSettings.xhr();
        n.crossDomain || (v["X-Requested-With"] = "XMLHttpRequest"), g && (v.Accept = g, g.indexOf(",") > -1 && ( g = g.split(",",2)[0]), w.overrideMimeType && w.overrideMimeType(g)), (n.contentType || n.data && "GET" != n.type.toUpperCase()) && (v["Content-Type"] = n.contentType || "application/x-www-form-urlencoded"), n.headers = t.extend(v, n.headers || {}), w.onreadystatechange = function() {
            if (4 == w.readyState) {
                clearTimeout(d);
                var t, e = !1;
                if (w.status >= 200 && w.status < 300 || 304 == w.status || 0 == w.status && "file:" == y) {
                    s = s || l(w.getResponseHeader("content-type")), t = w.responseText;
                    try {
                        "script" == s ? (1, eval)(t) : "xml" == s ? t = w.responseXML : "json" == s && ( t = S.test(t) ? null : JSON.parse(t))
                    } catch(i) {
                        e = i
                    }
                    e ? a(e, "parsererror", w, n) : o(t, w, n)
                } else
                    a(null, "error", w, n)
            }
        };
        var x = "async" in n ? n.async : !0;
        w.open(n.type, n.url, x);
        for (m in n.headers)
        w.setRequestHeader(m, n.headers[m]);
        return r(w, n) === !1 ? (w.abort(), !1) : (n.timeout > 0 && ( d = setTimeout(function() {
            w.onreadystatechange = c, w.abort(), a(null, "timeout", w, n)
        }, n.timeout)), w.send(n.data ? n.data : null), w)
    }, t.get = function(e, n) {
        return t.ajax({
            url : e,
            success : n
        })
    }, t.post = function(e, n, i, s) {
        return t.isFunction(n) && ( s = s || i, i = n, n = null), t.ajax({
            type : "POST",
            url : e,
            data : n,
            success : i,
            dataType : s
        })
    }, t.getJSON = function(e, n) {
        return t.ajax({
            url : e,
            success : n,
            dataType : "json"
        })
    }, t.fn.load = function(e, n) {
        if (!this.length)
            return this;
        var i, s = this, r = e.split(/\s/);
        return r.length > 1 && ( e = r[0], i = r[1]), t.get(e, function(e) {
            s.html( i ? t(y.createElement("div")).html(e.replace(w, "")).find(i).html() : e), n && n.call(s)
        }), this
    };
    var j = encodeURIComponent;
    t.param = function(t, e) {
        var n = [];
        return n.add = function(t, e) {
            this.push(j(t) + "=" + j(e))
        }, d(n, t, e), n.join("&").replace("%20", "+")
    }
}(Zepto), function(t) {
    t.fn.serializeArray = function() {
        var e, n = [];
        return t(Array.prototype.slice.call(this.get(0).elements)).each(function() {
            e = t(this);
            var i = e.attr("type");
            "fieldset" != this.nodeName.toLowerCase() && !this.disabled && "submit" != i && "reset" != i && "button" != i && ("radio" != i && "checkbox" != i || this.checked) && n.push({
                name : e.attr("name"),
                value : e.val()
            })
        }), n
    }, t.fn.serialize = function() {
        var t = [];
        return this.serializeArray().forEach(function(e) {
            t.push(encodeURIComponent(e.name) + "=" + encodeURIComponent(e.value))
        }), t.join("&")
    }, t.fn.submit = function(e) {
        if (e)
            this.bind("submit", e);
        else if (this.length) {
            var n = t.Event("submit");
            this.eq(0).trigger(n), n.defaultPrevented || this.get(0).submit()
        }
        return this
    }
}(Zepto), function(t) {
    function e(t) {
        return "tagName" in t ? t : t.parentNode
    }

    function n(t, e, n, i) {
        var s = Math.abs(t - e), r = Math.abs(n - i);
        return s >= r ? t - e > 0 ? "Left" : "Right" : n - i > 0 ? "Up" : "Down"
    }

    function i() {
        o = null, a.last && (a.el.trigger("longTap"), a = {})
    }

    function s() {
        o && clearTimeout(o), o = null
    }

    var r, o, a = {}, u = 750;
    t(document).ready(function() {
        var c, l;
        t(document.body).bind("touchstart", function(n) {
            c = Date.now(), l = c - (a.last || c), a.el = t(e(n.touches[0].target)), r && clearTimeout(r), a.x1 = n.touches[0].pageX, a.y1 = n.touches[0].pageY, l > 0 && 250 >= l && (a.isDoubleTap = !0), a.last = c, o = setTimeout(i, u)
        }).bind("touchmove", function(t) {
            s(), a.x2 = t.touches[0].pageX, a.y2 = t.touches[0].pageY
        }).bind("touchend", function() {
            s(), a.isDoubleTap ? (a.el.trigger("doubleTap"), a = {}) : a.x2 && Math.abs(a.x1 - a.x2) > 30 || a.y2 && Math.abs(a.y1 - a.y2) > 30 ? (a.el.trigger("swipe") && a.el.trigger("swipe" + n(a.x1, a.x2, a.y1, a.y2)), a = {}) : "last" in a && (a.el.trigger("tap"), r = setTimeout(function() {
                r = null, a.el.trigger("singleTap"), a = {}
            }, 250))
        }).bind("touchcancel", function() {
            r && clearTimeout(r), o && clearTimeout(o), o = r = null, a = {}
        })
    }), ["swipe", "swipeLeft", "swipeRight", "swipeUp", "swipeDown", "doubleTap", "tap", "singleTap", "longTap"].forEach(function(e) {
        t.fn[e] = function(t) {
            return this.bind(e, t)
        }
    })
}(Zepto), function(t, e, n, i) {
    var s = t(i), r = "waypoint.reached", o = function(t, n) {
        t.element.trigger(r, n), t.options.triggerOnce && t.element[e]("destroy")
    }, a = function(t, e) {
        for (var n = e.waypoints.length - 1; n >= 0 && e.waypoints[n].element[0] !== t[0]; )
            n -= 1;
        return n
    }, u = [], c = function(e) {
        t.extend(this, {
            element : t(e),
            oldScroll : 0,
            waypoints : [],
            didScroll : !1,
            didResize : !1,
            doScroll : t.proxy(function() {
                var e = this.element.scrollTop(), i = e > this.oldScroll, s = this, r = t.grep(this.waypoints, function(t) {
                    return i ? t.offset > s.oldScroll && t.offset <= e : t.offset <= s.oldScroll && t.offset > e
                }), a = r.length;
                this.oldScroll && e || t[n]("refresh"), this.oldScroll = e, a && (i || r.reverse(), t.each(r, function(t, e) {
                    (e.options.continuous || t === a - 1) && o(e, [ i ? "down" : "up"])
                }))
            }, this)
        }), t(e).scroll(t.proxy(function() {
            this.didScroll || (this.didScroll = !0, i.setTimeout(t.proxy(function() {
                this.doScroll(), this.didScroll = !1
            }, this), t[n].settings.scrollThrottle))
        }, this)).resize(t.proxy(function() {
            this.didResize || (this.didResize = !0, i.setTimeout(t.proxy(function() {
                t[n]("refresh"), this.didResize = !1
            }, this), t[n].settings.resizeThrottle))
        }, this)), s.load(t.proxy(function() {
            this.doScroll()
        }, this))
    }, l = function(e) {
        var n = null;
        return t.each(u, function(t, i) {
            return i.element[0] === e ? ( n = i, !1) :
            void 0
        }), n
    }, h = {
        init : function(i, s) {
            return this.each(function() {
                var o, h = t.fn[e].defaults.context, f = t(this);
                s && s.context && ( h = s.context), t.isWindow(h) || ( h = f.closest(h)[0]), o = l(h), o || ( o = new c(h), u.push(o));
                var d = a(f, o), p = 0 > d ? t.fn[e].defaults : o.waypoints[d].options, m = t.extend({}, p, s);
                m.offset = "bottom-in-view" === m.offset ? function() {
                    var e = t.isWindow(h) ? t[n]("viewportHeight") : t(h).height();
                    return e - t(this).outerHeight()
                } : m.offset, 0 > d ? o.waypoints.push({
                    element : f,
                    offset : null,
                    options : m
                }) : o.waypoints[d].options = m, i && f.bind(r, i), s && s.handler && f.bind(r, s.handler)
            }), t[n]("refresh"), this
        },
        remove : function() {
            return this.each(function(e, n) {
                var i = t(n);
                t.each(u, function(t, e) {
                    var n = a(i, e);
                    n >= 0 && e.waypoints.splice(n, 1)
                })
            })
        },
        destroy : function() {
            return this.unbind(r)[e]("remove")
        }
    }, f = {
        refresh : function() {
            t.each(u, function(e, i) {
                var s = t.isWindow(i.element[0]), r = s ? 0 : i.element.offset().top, a = s ? t[n]("viewportHeight") : i.element.height(), u = s ? 0 : i.element.scrollTop();
                t.each(i.waypoints, function(t, e) {
                    if (e) {
                        var n = e.options.offset, s = e.offset;
                        if ("function" == typeof e.options.offset)
                            n = e.options.offset.apply(e.element);
                        else if ("string" == typeof e.options.offset) {
                            var c = parseFloat(e.options.offset);
                            n = e.options.offset.indexOf("%") ? Math.ceil(a * (c / 100)) : c
                        }
                        e.offset = e.element.offset().top - r + u - n, e.options.onlyOnScroll || (null !== s && i.oldScroll > s && i.oldScroll <= e.offset ? o(e, ["up"]) : null !== s && i.oldScroll < s && i.oldScroll >= e.offset ? o(e, ["down"]) : !s && u > e.offset && o(e, ["down"]))
                    }
                }), i.waypoints.sort(function(t, e) {
                    return t.offset - e.offset
                })
            })
        },
        viewportHeight : function() {
            return i.innerHeight ? i.innerHeight : s.height()
        },
        aggregate : function() {
            var e = t();
            return t.each(u, function(n, i) {
                t.each(i.waypoints, function(t, n) {
                    e = e.add(n.element)
                })
            }), e
        }
    };
    t.fn[e] = function(n) {
        return h[n] ? h[n].apply(this, Array.prototype.slice.call(arguments, 1)) : "function" != typeof n && n ? "object" == typeof n ? h.init.apply(this, [null, n]) :
        void  t.error("Method " + n + " does not exist on jQuery " + e) : h.init.apply(this, arguments)
    }, t.fn[e].defaults = {
        continuous : !0,
        offset : 0,
        triggerOnce : !1,
        context : i
    }, t[n] = function(t) {
        return f[t] ? f[t].apply(this) : f.aggregate()
    }, t[n].settings = {
        resizeThrottle : 200,
        scrollThrottle : 100
    }, s.load(function() {
        t[n]("refresh")
    })
}(jQuery, "waypoint", "waypoints", this); 