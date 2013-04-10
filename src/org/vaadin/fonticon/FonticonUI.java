package org.vaadin.fonticon;

import java.util.Arrays;
import java.util.Set;

import com.porotype.iconfont.FontAwesome;
import com.porotype.iconfont.FontAwesome.Icon;
import com.porotype.iconfont.FontAwesome.IconVariant;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * Main UI class
 */
@SuppressWarnings("serial")
public class FonticonUI extends UI {

    Label demoLabel;
    Button demoButton;
    NativeButton demoNativeButton;

    @Override
    protected void init(VaadinRequest request) {
        FontAwesome.load(); // REMEMBER to load!

        // Main layout
        final VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        setContent(layout);

        // Intro label w/ various icons
        Label label = new Label(
                Icon.info_sign.variant(IconVariant.PULL_LEFT,
                        IconVariant.SIZE_4X)
                        + "This demonstrates some of the use-cases for font icons in Vaadin.<br/>"
                        + Icon.warning_sign
                        + " Please note that a font-icon is not a <code>Resource</code>, and can not be used as a"
                        + " regular Vaadin icon using <code>setIcon()</code>.<br> Instead, you can add a font-icon anywhere "
                        + Icon.exclamation_sign
                        + " where you can use formatted HTML, including tooltips.",
                ContentMode.HTML);
        layout.addComponent(label);

        // Playground
        HorizontalLayout hz = new HorizontalLayout();
        hz.setSpacing(true);
        hz.setCaption("Icon playground");
        layout.addComponent(hz);
        // Icon/variant selects
        VerticalLayout vl = new VerticalLayout();
        vl.setStyleName(Reindeer.LAYOUT_BLUE);
        vl.setMargin(true);
        hz.addComponent(vl);
        final ComboBox icon = new ComboBox("Icon", Arrays.asList(Icon.values())) {
            @Override
            public String getItemCaption(Object itemId) {
                // format the caption in the combobox
                return ((Icon) itemId).name();
            }
        };
        vl.addComponent(icon);
        icon.setNullSelectionAllowed(false);
        icon.setImmediate(true);
        final TwinColSelect variant = new TwinColSelect("Variant(s)",
                Arrays.asList(IconVariant.values()));
        variant.setImmediate(true);
        vl.addComponent(variant);
        // Demo components
        vl = new VerticalLayout();
        vl.setSpacing(true);
        hz.addComponent(vl);
        demoLabel = new Label("", ContentMode.HTML); // REMEMBER ContentMode
        vl.addComponent(demoLabel);
        demoButton = new Button();
        demoButton.setHtmlContentAllowed(true); // REMEMBER HtmlAllowed
        vl.addComponent(demoButton);
        demoNativeButton = new NativeButton();
        demoNativeButton.setHtmlContentAllowed(true); // REMEMBER HtmlAllowed
        vl.addComponent(demoNativeButton);
        // listeners
        icon.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                setDemoText(icon, variant);
            }
        });
        variant.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                setDemoText(icon, variant);
            }
        });
        icon.select(Icon.adjust);

        // ALL the icons
        final CssLayout icons = new CssLayout();
        icons.setCaption("ALL THE THINGS!!!");
        layout.addComponent(icons);
        for (Icon i : Icon.values()) {
            Button b = new Button(i + " " + i.name());
            b.setHtmlContentAllowed(true);
            icons.addComponent(b);
            b.setDescription(i + " " + i.name());
        }

    }

    private void setDemoText(AbstractSelect icon, AbstractSelect variant) {
        Set<IconVariant> variants = (Set<IconVariant>) variant.getValue();
        String s = ((Icon) icon.getValue()).variant(variants
                .toArray(new IconVariant[] {}));

        demoLabel
                .setValue(s
                        + " The quick brown fox jumps over the lazy dog.<br/>The quick brown fox jumps over<br/> the lazy dog.");
        demoLabel.setDescription(s + " Demo tooltip");
        demoButton.setCaption(s + " Button");
        demoButton.setDescription(s + " Demo tooltip");
        demoNativeButton.setCaption(s + " Native Button");
        demoNativeButton.setDescription(s + " Demo tooltip");
    }

}