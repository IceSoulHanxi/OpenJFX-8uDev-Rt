package com.sun.javafx.scene.control.skin;

import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.css.converters.EnumConverter;
import com.sun.javafx.css.converters.PaintConverter;
import com.sun.javafx.css.converters.SizeConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.css.CssMetaData;
import javafx.css.FontCssMetaData;
import javafx.css.StyleOrigin;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.scene.control.Labeled;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * LabeledText allows the Text to be styled by the CSS properties of Labeled
 * that are meant to style the textual component of the Labeled.
 *
 * LabeledText has the style class "text"
 */
public class LabeledText extends Text {

   private final Labeled labeled;

   public LabeledText(Labeled labeled) {
       super();

       if (labeled == null) {
           throw new IllegalArgumentException("labeled cannot be null");
       }

       this.labeled = labeled;

       //
       // init the state of this Text object to that of the Labeled
       //
       this.setFill(this.labeled.getTextFill());
       this.setFont(this.labeled.getFont());
       this.setTextAlignment(this.labeled.getTextAlignment());
       this.setUnderline(this.labeled.isUnderline());
       this.setLineSpacing(this.labeled.getLineSpacing());

       //
       // Bind the state of this Text object to that of the Labeled.
       // Binding these properties prevents CSS from setting them
       //
       this.fillProperty().bind(this.labeled.textFillProperty());
       this.fontProperty().bind(this.labeled.fontProperty());
       // do not bind text - Text doesn't have -fx-text
       this.textAlignmentProperty().bind(this.labeled.textAlignmentProperty());
       this.underlineProperty().bind(this.labeled.underlineProperty());
       this.lineSpacingProperty().bind(this.labeled.lineSpacingProperty());

       getStyleClass().addAll("text");
   }

    /**
     * @return The CssMetaData associated with this class, which may include the
     * CssMetaData of its super classes.
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

   //
   // Replace all of Text's CssMetaData instances that overlap with Labeled
   // with instances of CssMetaData that redirect to Labeled. Thus, when
   // the Labeled is styled,
   //
   private static class StyleableProperties {

       private static final CssMetaData<LabeledText,Font> FONT =
           new FontCssMetaData<LabeledText>("-fx-font", Font.getDefault()) {

            @Override
            public void set(LabeledText node, Font value, StyleOrigin origin) {
                //
                // In the case where the Labeled's textFill was set by an
                // inline style, this inline style should override values
                // from lesser origins.
                //
                StyleableProperty<Font> prop = (StyleableProperty<Font>)node.labeled.fontProperty();
                StyleOrigin propOrigin = prop.getStyleOrigin();

                //
                // if propOrigin is null, then the property is in init state
                // if origin is null, then some code is initializing this prop
                // if propOrigin is greater than origin, then the style should
                //    not override
                //
                if (propOrigin == null ||
                    origin == null ||
                    propOrigin.compareTo(origin) <= 0) {
                    super.set(node, value, origin);
                }
            }

           @Override
           public boolean isSettable(LabeledText node) {
               return node.labeled != null ? node.labeled.fontProperty().isBound() == false : true;
           }

           @Override
           public StyleableProperty<Font> getStyleableProperty(LabeledText node) {
               return node.labeled != null ? (StyleableProperty<Font>)node.labeled.fontProperty() : null;
           }
       };


       private static final CssMetaData<LabeledText,Paint> FILL =
           new CssMetaData<LabeledText,Paint>("-fx-fill",
               PaintConverter.getInstance(), Color.BLACK) {

            @Override
            public void set(LabeledText node, Paint value, StyleOrigin origin) {
                //
                // In the case where the Labeled's textFill was set by an
                // inline style, this inline style should override values
                // from lesser origins.
                //
                StyleableProperty<Paint> prop = 
                        (StyleableProperty<Paint>)node.labeled.textFillProperty();
                StyleOrigin propOrigin = prop.getStyleOrigin();

                //
                // if propOrigin is null, then the property is in init state
                // if origin is null, then some code is initializing this prop
                // if propOrigin is greater than origin, then the style should
                //    not override
                //
                if (propOrigin == null ||
                    origin == null ||
                    propOrigin.compareTo(origin) <= 0) {
                    super.set(node, value, origin);
                }
            }

           @Override
           public boolean isSettable(LabeledText node) {
               return node.labeled.textFillProperty().isBound() == false;
           }

           @Override
           public StyleableProperty<Paint> getStyleableProperty(LabeledText node) {
               return (StyleableProperty<Paint>)node.labeled.textFillProperty();
           }
       };

        private static final CssMetaData<LabeledText,TextAlignment> TEXT_ALIGNMENT =
                new CssMetaData<LabeledText,TextAlignment>("-fx-text-alignment",
                new EnumConverter<TextAlignment>(TextAlignment.class),
                TextAlignment.LEFT) {

            @Override
            public void set(LabeledText node, TextAlignment value, StyleOrigin origin) {
                //
                // In the case where the Labeled's textFill was set by an
                // inline style, this inline style should override values
                // from lesser origins.
                //
                StyleableProperty<TextAlignment> prop = 
                        (StyleableProperty<TextAlignment>)node.labeled.textAlignmentProperty();
                StyleOrigin propOrigin = prop.getStyleOrigin();

                //
                // if propOrigin is null, then the property is in init state
                // if origin is null, then some code is initializing this prop
                // if propOrigin is greater than origin, then the style should
                //    not override
                //
                if (propOrigin == null ||
                    origin == null ||
                    propOrigin.compareTo(origin) <= 0) {
                    super.set(node, value, origin);
                }
            }

            @Override
            public boolean isSettable(LabeledText node) {
                return node.labeled.textAlignmentProperty().isBound() == false;
            }

            @Override
            public StyleableProperty<TextAlignment> getStyleableProperty(LabeledText node) {
                return (StyleableProperty<TextAlignment>)node.labeled.textAlignmentProperty();
            }
        };

        private static final CssMetaData<LabeledText,Boolean> UNDERLINE =
                new CssMetaData<LabeledText,Boolean>("-fx-underline",
                BooleanConverter.getInstance(),
                Boolean.FALSE) {

            @Override
            public void set(LabeledText node, Boolean value, StyleOrigin origin) {
                //
                // In the case where the Labeled's textFill was set by an
                // inline style, this inline style should override values
                // from lesser origins.
                //
                StyleableProperty<Boolean> prop = 
                        (StyleableProperty<Boolean>)node.labeled.underlineProperty();
                StyleOrigin propOrigin = prop.getStyleOrigin();

                //
                // if propOrigin is null, then the property is in init state
                // if origin is null, then some code is initializing this prop
                // if propOrigin is greater than origin, then the style should
                //    not override
                //
                if (propOrigin == null ||
                    origin == null ||
                    propOrigin.compareTo(origin) <= 0) {
                    super.set(node, value, origin);
                }
            }

            @Override
            public boolean isSettable(LabeledText node) {
                return node.labeled.underlineProperty().isBound() == false;
            }

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(LabeledText node) {
                return (StyleableProperty<Boolean>)node.labeled.underlineProperty();
            }
        };

        private static final CssMetaData<LabeledText,Number> LINE_SPACING =
	    new CssMetaData<LabeledText,Number>("-fx-line-spacing",
                SizeConverter.getInstance(),
                0) {

            @Override
            public void set(LabeledText node, Number value, StyleOrigin origin) {
                //
                // In the case where the Labeled's textFill was set by an
                // inline style, this inline style should override values
                // from lesser origins.
                //
                StyleableProperty<Number> prop = 
                        (StyleableProperty<Number>)node.labeled.lineSpacingProperty();
                StyleOrigin propOrigin = prop.getStyleOrigin();

                //
                // if propOrigin is null, then the property is in init state
                // if origin is null, then some code is initializing this prop
                // if propOrigin is greater than origin, then the style should
                //    not override
                //
                if (propOrigin == null ||
                    origin == null ||
                    propOrigin.compareTo(origin) <= 0) {
                    super.set(node, value, origin);
                }
            }

            @Override
            public boolean isSettable(LabeledText node) {
                return node.labeled.lineSpacingProperty().isBound() == false;
            }

            @Override
            public StyleableProperty<Number> getStyleableProperty(LabeledText node) {
                return (StyleableProperty<Number>)node.labeled.lineSpacingProperty();
            }
        };

       private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
       static {

           final List<CssMetaData<? extends Styleable, ?>> styleables =
               new ArrayList<CssMetaData<? extends Styleable, ?>>(Text.getClassCssMetaData());

           for (int n=0,nMax=styleables.size(); n<nMax; n++) {
               final String prop = styleables.get(n).getProperty();

               if ("-fx-fill".equals(prop)) {
                   styleables.set(n, FILL);
               } else if ("-fx-font".equals(prop)) {
                   styleables.set(n, FONT);
               } else if ("-fx-text-alignment".equals(prop)) {
                   styleables.set(n, TEXT_ALIGNMENT);
               } else if ("-fx-underline".equals(prop)) {
                   styleables.set(n, UNDERLINE);
               } else if ("-fx-line-spacing".equals(prop)) {
                   styleables.set(n, LINE_SPACING);
               }
           }

           STYLEABLES = Collections.unmodifiableList(styleables);
       }
   }
}
