package com.cl932.rsmw.web;

import com.cl932.rsmw.SpringContext;
import com.cl932.rsmw.controller.PDZController;
import com.cl932.rsmw.controller.PDZDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.csp.CSPDirective;
import org.apache.wicket.csp.CSPDirectiveSrcValue;
import org.apache.wicket.markup.head.*;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.ChainingModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.io.IClusterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@WicketHomePage
public class HomePage extends BasePage {


    PDZController pdzController;
    public HomePage() {
        add(new PdzForm("pdzForm"));
    }
    {
        pdzController = SpringContext.getBean(PDZController.class);
    }
    private class PdzForm extends StatelessForm<PdzForm> {
        CheckBox checkBox = new CheckBox("bool");
        String PDK_CO;
        String PDK_CO2;
        String PDK_NO;
        String PDK_NO2;
        String PDK_NOx;
        String PDK_SO2;
        String PDK_CH4;
        String PDK_dust;
        String PDV_CO;
        String PDV_CO2;
        String PDV_NO;
        String PDV_NO2;
        String PDV_NOx;
        String PDV_SO2;
        String PDV_CH4;
        String PDV_dust;
        String mode;
        Boolean bool = Boolean.FALSE;

        public PdzForm(String id) {
            super(id);
            WebApplication.get().getCspSettings().blocking().clear().add(CSPDirective.DEFAULT_SRC, CSPDirectiveSrcValue.NONE)
                    .add(CSPDirective.STYLE_SRC, CSPDirectiveSrcValue.SELF)
                    .add(CSPDirective.SCRIPT_SRC, CSPDirectiveSrcValue.UNSAFE_INLINE, CSPDirectiveSrcValue.SELF)
                    .add(CSPDirective.IMG_SRC, CSPDirectiveSrcValue.SELF)
                    .add(CSPDirective.FONT_SRC, CSPDirectiveSrcValue.SELF);
            setModel(new CompoundPropertyModel<>(this));
            add(new TextField<String>("PDK_CO"));
            add(new TextField<String>("PDK_CO2"));
            add(new TextField<String>("PDK_NO"));
            add(new TextField<String>("PDK_NO2"));
            add(new TextField<String>("PDK_NOx"));
            add(new TextField<String>("PDK_SO2"));
            add(new TextField<String>("PDK_CH4"));
            add(new TextField<String>("PDK_dust"));
            add(new TextField<String>("PDV_CO"));
            add(new TextField<String>("PDV_CO2"));
            add(new TextField<String>("PDV_NO"));
            add(new TextField<String>("PDV_NO2"));
            add(new TextField<String>("PDV_NOx"));
            add(new TextField<String>("PDV_SO2"));
            add(new TextField<String>("PDV_CH4"));
            add(new TextField<String>("PDV_dust"));
            add(new TextField<Integer>("mode"));
            add(checkBox);
        }

        @Override
        protected void onSubmit() {
            System.out.println(mode);

            boolean isMode = Boolean.parseBoolean(checkBox.getValue());
            System.out.println(isMode);
            if (isMode) {
                setPdvMode();
            } else {
                setDirectPdv();
            }

        }
        @Override
        public void renderHead(IHeaderResponse response) {
            response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(HomePage.class, "script.js")));
            response.render(CssHeaderItem.forReference(new PackageResourceReference(HomePage.class, "style.css")));
        }

        private void setDirectPdv(){

            try {
                pdzController.setPDZ(PDZDto.builder().PDK_CO(PDK_CO)
                        .PDK_CO2(PDK_CO2)
                        .PDK_NO(PDK_NO)
                        .PDK_NO2(PDK_NO2)
                        .PDK_NOx(PDK_NOx)
                        .PDK_SO2(PDK_SO2)
                        .PDK_CH4(PDK_CH4)
                        .PDK_dust(PDK_dust)
                        .PDV_CO(PDV_CO)
                        .PDV_CO2(PDV_CO2)
                        .PDV_NO(PDV_NO)
                        .PDV_NO2(PDV_NO2)
                        .PDV_NOx(PDV_NOx)
                        .PDV_SO2(PDV_SO2)
                        .PDV_CH4(PDV_CH4)
                        .PDV_dust(PDV_dust)
                        .build());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        private void setPdvMode() {
            Modes selectedMode = switch (Integer.parseInt(mode)) {
                case 1 -> Modes.MODE1;
                case 2 -> Modes.MODE2;
                case 3 -> Modes.MODE3;
                case 4 -> Modes.MODE4;
                case 5 -> Modes.MODE5;
                case 6 -> Modes.MODE6;
                case 7 -> Modes.MODE7;
                case 8 -> Modes.MODE8;
                case 9 -> Modes.MODE9;
                case 10 -> Modes.MODE10;
                case 11 -> Modes.MODE11;
                case 12 -> Modes.MODE12;
                case 13 -> Modes.MODE13;
                case 14 -> Modes.MODE14;
                case 15 -> Modes.MODE15;
                case 16 -> Modes.MODE16;
                default -> Modes.MODE1;
            };
            double[] selectedModeDoubles = selectedMode.toDouble();
            try {
                pdzController.setPDZ(PDZDto.builder()
                        .PDK_CO(String.valueOf(selectedModeDoubles[3]))
                        .PDK_CO2("0")
                        .PDK_NO("0")
                        .PDK_NO2(String.valueOf(selectedModeDoubles[0]))
                        .PDK_NOx("0")
                        .PDK_SO2(String.valueOf(selectedModeDoubles[1]))
                        .PDK_CH4("0")
                        .PDK_dust(String.valueOf(selectedModeDoubles[2]))
                        .PDV_CO("30.518")
                        .PDV_CO2("0")
                        .PDV_NO("1.47612253")
                        .PDV_NO2("35.861")
                        .PDV_NOx("0")
                        .PDV_SO2("298.42")
                        .PDV_CH4("0")
                        .PDV_dust("0")
                        .build());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


