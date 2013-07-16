/*
    This file is part of the WebKit open source project.
    This file has been generated by generate-bindings.pl. DO NOT MODIFY!

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Library General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Library General Public License for more details.

    You should have received a copy of the GNU Library General Public License
    along with this library; see the file COPYING.LIB.  If not, write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA 02110-1301, USA.
*/

#if !defined(__WEBKITDOM_H_INSIDE__) && !defined(BUILDING_WEBKIT)
#error "Only <webkitdom/webkitdom.h> can be included directly."
#endif

#ifndef WebKitDOMTestTypedefs_h
#define WebKitDOMTestTypedefs_h

#include <glib-object.h>
#include <webkitdom/WebKitDOMObject.h>
#include <webkitdom/webkitdomdefines.h>

G_BEGIN_DECLS

#define WEBKIT_TYPE_DOM_TEST_TYPEDEFS            (webkit_dom_test_typedefs_get_type())
#define WEBKIT_DOM_TEST_TYPEDEFS(obj)            (G_TYPE_CHECK_INSTANCE_CAST((obj), WEBKIT_TYPE_DOM_TEST_TYPEDEFS, WebKitDOMTestTypedefs))
#define WEBKIT_DOM_TEST_TYPEDEFS_CLASS(klass)    (G_TYPE_CHECK_CLASS_CAST((klass),  WEBKIT_TYPE_DOM_TEST_TYPEDEFS, WebKitDOMTestTypedefsClass)
#define WEBKIT_DOM_IS_TEST_TYPEDEFS(obj)         (G_TYPE_CHECK_INSTANCE_TYPE((obj), WEBKIT_TYPE_DOM_TEST_TYPEDEFS))
#define WEBKIT_DOM_IS_TEST_TYPEDEFS_CLASS(klass) (G_TYPE_CHECK_CLASS_TYPE((klass),  WEBKIT_TYPE_DOM_TEST_TYPEDEFS))
#define WEBKIT_DOM_TEST_TYPEDEFS_GET_CLASS(obj)  (G_TYPE_INSTANCE_GET_CLASS((obj),  WEBKIT_TYPE_DOM_TEST_TYPEDEFS, WebKitDOMTestTypedefsClass))

struct _WebKitDOMTestTypedefs {
    WebKitDOMObject parent_instance;
};

struct _WebKitDOMTestTypedefsClass {
    WebKitDOMObjectClass parent_class;
};

WEBKIT_API GType
webkit_dom_test_typedefs_get_type (void);

/**
 * webkit_dom_test_typedefs_func:
 * @self: A #WebKitDOMTestTypedefs
 * @x: A #WebKitDOMlong[]
 *
 * Returns:
 *
**/
WEBKIT_API void
webkit_dom_test_typedefs_func(WebKitDOMTestTypedefs* self, WebKitDOMlong[]* x);

/**
 * webkit_dom_test_typedefs_set_shadow:
 * @self: A #WebKitDOMTestTypedefs
 * @width: A #gfloat
 * @height: A #gfloat
 * @blur: A #gfloat
 * @color: A #gchar
 * @alpha: A #gfloat
 *
 * Returns:
 *
**/
WEBKIT_API void
webkit_dom_test_typedefs_set_shadow(WebKitDOMTestTypedefs* self, gfloat width, gfloat height, gfloat blur, const gchar* color, gfloat alpha);

/**
 * webkit_dom_test_typedefs_nullable_array_arg:
 * @self: A #WebKitDOMTestTypedefs
 * @arrayArg: A #WebKitDOMDOMString[]
 *
 * Returns:
 *
**/
WEBKIT_API void
webkit_dom_test_typedefs_nullable_array_arg(WebKitDOMTestTypedefs* self, WebKitDOMDOMString[]* arrayArg);

/**
 * webkit_dom_test_typedefs_immutable_point_function:
 * @self: A #WebKitDOMTestTypedefs
 *
 * Returns: (transfer none):
 *
**/
WEBKIT_API WebKitDOMSVGPoint*
webkit_dom_test_typedefs_immutable_point_function(WebKitDOMTestTypedefs* self);

/**
 * webkit_dom_test_typedefs_string_array_function:
 * @self: A #WebKitDOMTestTypedefs
 * @values: A #WebKitDOMDOMString[]
 * @error: #GError
 *
 * Returns: (transfer none):
 *
**/
WEBKIT_API WebKitDOMDOMString[]*
webkit_dom_test_typedefs_string_array_function(WebKitDOMTestTypedefs* self, WebKitDOMDOMString[]* values, GError** error);

/**
 * webkit_dom_test_typedefs_string_array_function2:
 * @self: A #WebKitDOMTestTypedefs
 * @values: A #WebKitDOMDOMString[]
 * @error: #GError
 *
 * Returns: (transfer none):
 *
**/
WEBKIT_API WebKitDOMDOMString[]*
webkit_dom_test_typedefs_string_array_function2(WebKitDOMTestTypedefs* self, WebKitDOMDOMString[]* values, GError** error);

/**
 * webkit_dom_test_typedefs_method_with_exception:
 * @self: A #WebKitDOMTestTypedefs
 * @error: #GError
 *
 * Returns:
 *
**/
WEBKIT_API void
webkit_dom_test_typedefs_method_with_exception(WebKitDOMTestTypedefs* self, GError** error);

/**
 * webkit_dom_test_typedefs_get_unsigned_long_long_attr:
 * @self: A #WebKitDOMTestTypedefs
 *
 * Returns:
 *
**/
WEBKIT_API guint64
webkit_dom_test_typedefs_get_unsigned_long_long_attr(WebKitDOMTestTypedefs* self);

/**
 * webkit_dom_test_typedefs_set_unsigned_long_long_attr:
 * @self: A #WebKitDOMTestTypedefs
 * @value: A #guint64
 *
 * Returns:
 *
**/
WEBKIT_API void
webkit_dom_test_typedefs_set_unsigned_long_long_attr(WebKitDOMTestTypedefs* self, guint64 value);

/**
 * webkit_dom_test_typedefs_get_immutable_serialized_script_value:
 * @self: A #WebKitDOMTestTypedefs
 *
 * Returns: (transfer none):
 *
**/
WEBKIT_API WebKitDOMSerializedScriptValue*
webkit_dom_test_typedefs_get_immutable_serialized_script_value(WebKitDOMTestTypedefs* self);

/**
 * webkit_dom_test_typedefs_set_immutable_serialized_script_value:
 * @self: A #WebKitDOMTestTypedefs
 * @value: A #WebKitDOMSerializedScriptValue
 *
 * Returns: (transfer none):
 *
**/
WEBKIT_API void
webkit_dom_test_typedefs_set_immutable_serialized_script_value(WebKitDOMTestTypedefs* self, WebKitDOMSerializedScriptValue* value);

/**
 * webkit_dom_test_typedefs_get_attr_with_getter_exception:
 * @self: A #WebKitDOMTestTypedefs
 * @error: #GError
 *
 * Returns:
 *
**/
WEBKIT_API glong
webkit_dom_test_typedefs_get_attr_with_getter_exception(WebKitDOMTestTypedefs* self, GError** error);

/**
 * webkit_dom_test_typedefs_set_attr_with_getter_exception:
 * @self: A #WebKitDOMTestTypedefs
 * @value: A #glong
 *
 * Returns:
 *
**/
WEBKIT_API void
webkit_dom_test_typedefs_set_attr_with_getter_exception(WebKitDOMTestTypedefs* self, glong value);

/**
 * webkit_dom_test_typedefs_get_attr_with_setter_exception:
 * @self: A #WebKitDOMTestTypedefs
 *
 * Returns:
 *
**/
WEBKIT_API glong
webkit_dom_test_typedefs_get_attr_with_setter_exception(WebKitDOMTestTypedefs* self);

/**
 * webkit_dom_test_typedefs_set_attr_with_setter_exception:
 * @self: A #WebKitDOMTestTypedefs
 * @value: A #glong
 * @error: #GError
 *
 * Returns:
 *
**/
WEBKIT_API void
webkit_dom_test_typedefs_set_attr_with_setter_exception(WebKitDOMTestTypedefs* self, glong value, GError** error);

/**
 * webkit_dom_test_typedefs_get_string_attr_with_getter_exception:
 * @self: A #WebKitDOMTestTypedefs
 * @error: #GError
 *
 * Returns:
 *
**/
WEBKIT_API gchar*
webkit_dom_test_typedefs_get_string_attr_with_getter_exception(WebKitDOMTestTypedefs* self, GError** error);

/**
 * webkit_dom_test_typedefs_set_string_attr_with_getter_exception:
 * @self: A #WebKitDOMTestTypedefs
 * @value: A #gchar
 *
 * Returns:
 *
**/
WEBKIT_API void
webkit_dom_test_typedefs_set_string_attr_with_getter_exception(WebKitDOMTestTypedefs* self, const gchar* value);

/**
 * webkit_dom_test_typedefs_get_string_attr_with_setter_exception:
 * @self: A #WebKitDOMTestTypedefs
 *
 * Returns:
 *
**/
WEBKIT_API gchar*
webkit_dom_test_typedefs_get_string_attr_with_setter_exception(WebKitDOMTestTypedefs* self);

/**
 * webkit_dom_test_typedefs_set_string_attr_with_setter_exception:
 * @self: A #WebKitDOMTestTypedefs
 * @value: A #gchar
 * @error: #GError
 *
 * Returns:
 *
**/
WEBKIT_API void
webkit_dom_test_typedefs_set_string_attr_with_setter_exception(WebKitDOMTestTypedefs* self, const gchar* value, GError** error);

G_END_DECLS

#endif /* WebKitDOMTestTypedefs_h */
