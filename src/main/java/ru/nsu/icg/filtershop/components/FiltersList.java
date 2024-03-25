package ru.nsu.icg.filtershop.components;

import lombok.Getter;
import ru.nsu.icg.filtershop.model.tools.*;
import ru.nsu.icg.filtershop.model.tools.channels.BlueChannelTool;
import ru.nsu.icg.filtershop.model.tools.channels.GreenChannelTool;
import ru.nsu.icg.filtershop.model.tools.channels.RedChannelTool;
import ru.nsu.icg.filtershop.model.tools.dithering.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FiltersList {

    @Getter
    private final FiltershopToolBar toolBar;

    @Getter
    private final JMenu toolsMenu;

    private final List<ToolOption> tools;
    private final List<AbstractButton> toolBarGroup;
    private final List<AbstractButton> menuBarGroup;

    private final Consumer<? super Tool> onToolSelect;
    private final Runnable onCancel;

    private ToolOption lastSelected;

    public FiltersList(Consumer<? super Tool> onToolSelect,
                       Runnable onCancel,
                       FiltershopToolBar mainToolBar) {
        this.onToolSelect = onToolSelect;
        this.onCancel = onCancel;

        toolBar = mainToolBar;
        toolsMenu = new JMenu("Filter");
        tools = new ArrayList<>();

        createBlackWhite();
        createInversion();
        createGamma();
        createBlur();
        createEmbossing();
        createMedian();
        createRobertsHighlight();
        createSobelHighlight();
        createSharpness();
        createWatercolor();
        createPixelArt();
        createWave();
        createSwirl();
        createSepia();
        createRedChannel();
        createGreenChannel();
        createBlueChannel();
        createFloydSteinbergDithering();
        createOrderedDithering();
        createFloydSteinbergSartakovDithering();
        createOrderedDitheringSartakov();
        createFloydSteinbergNazarovDithering();
        createOrderedNazarovDithering();

        toolBarGroup = new ArrayList<>();
        menuBarGroup = new ArrayList<>();
        for (ToolOption tool : tools) {
            toolBar.add(tool.getRadioButton());
            toolBarGroup.add(tool.getRadioButton());

            toolsMenu.add(tool.getMenuItem());
            menuBarGroup.add(tool.getMenuItem());
        }
    }

    private void createSwirl() {
        ParameterToolOption waveOption = new ParameterToolOption("Swirl", onCancel,
                List.of(
                        Parameters.builder().name("swirlFactor").min(-50).max(50).initial(0)
                                .build()
                ));
        waveOption.setOnToolSelect(tool -> select(tool, waveOption.getToolOption()));
        waveOption.setToolSupplier(() -> new SwirlTool(
                (int) waveOption.getParameter("swirlFactor")
        ));
        tools.add(waveOption.getToolOption());
        waveOption.getToolOption().setIcons("/icons/wirl_tool.png",
                "/icons/wirl_selected_tool.png"
        );
    }

    public void setLastSelected(boolean b) {
        if (lastSelected == null) {
            return;
        }
        lastSelected.getRadioButton().setSelected(b);
        lastSelected.getMenuItem().setSelected(b);
    }

    private void select(Tool tool, ToolOption option) {
        lastSelected = option;

        toolBarGroup.forEach(b -> b.setSelected(false));
        menuBarGroup.forEach(b -> b.setSelected(false));

        option.getRadioButton().setSelected(true);
        option.getMenuItem().setSelected(true);

        onToolSelect.accept(tool);
    }

    private void createOrderedDitheringSartakov() {
        ParameterToolOption ditheringOption = new ParameterToolOption("Ordered Sartakov dithering",
                onCancel, List.of(
                Parameters.builder().name("quantization red").min(2).max(128).initial(2)
                        .build(),
                Parameters.builder().name("quantization green").min(2).max(128).initial(2)
                        .build(),
                Parameters.builder().name("quantization blue").min(2).max(128).initial(2)
                        .build()
        ));
        ditheringOption.setOnToolSelect(tool -> select(tool, ditheringOption.getToolOption()));
        ditheringOption.setToolSupplier(() -> new OrderedDitheringSartakovTool(
                (int) ditheringOption.getParameter("quantization red"),
                (int) ditheringOption.getParameter("quantization green"),
                (int) ditheringOption.getParameter("quantization blue")
        ));
        tools.add(ditheringOption.getToolOption());
        ditheringOption.getToolOption().setIcons("/icons/dithering2_icon.png",
                "/icons/dithering2_selected_icon.png");
    }

    private void createFloydSteinbergSartakovDithering() {
        ParameterToolOption ditheringOption = new ParameterToolOption("Floyd-Steinberg Sartakov dithering",
                onCancel, List.of(
                Parameters.builder().name("red quantization").min(2).max(128).initial(2)
                        .build(),
                Parameters.builder().name("green quantization").min(2).max(128).initial(2)
                        .build(),
                Parameters.builder().name("blue quantization").min(2).max(128).initial(2)
                        .build()
        ));
        ditheringOption.setOnToolSelect(tool -> select(tool, ditheringOption.getToolOption()));
        ditheringOption.setToolSupplier(() -> new FloydSteinbergSartakovTool(
                (int) ditheringOption.getParameter("red quantization"),
                (int) ditheringOption.getParameter("green quantization"),
                (int) ditheringOption.getParameter("blue quantization")
        ));
        tools.add(ditheringOption.getToolOption());
        ditheringOption.getToolOption().setIcons("/icons/dithering1_icon.png",
                "/icons/dithering1_selected_icon.png");
    }

    private void createBlueChannel() {
        ToolOption blueChannelOption = new ToolOption("Blue channel",
                new BlueChannelTool(), () -> cancelSelection(onCancel));
        blueChannelOption.setOnSelect(tool -> select(tool, blueChannelOption));
        tools.add(blueChannelOption);
        blueChannelOption.setIcons("/icons/blue_channel_icon.png",
                "/icons/blue_channel_selected_icon.png"
        );
    }

    private void createGreenChannel() {
        ToolOption greenChannelOption = new ToolOption("Green channel",
                new GreenChannelTool(), () -> cancelSelection(onCancel));
        greenChannelOption.setOnSelect(tool -> select(tool, greenChannelOption));
        tools.add(greenChannelOption);
        greenChannelOption.setIcons("/icons/green_channel_icon.png",
                "/icons/green_channel_selected_icon.png"
        );
    }

    private void createRedChannel() {
        ToolOption redChannelOption = new ToolOption("Red channel",
                new RedChannelTool(), () -> cancelSelection(onCancel));
        redChannelOption.setOnSelect(tool -> select(tool, redChannelOption));
        tools.add(redChannelOption);
        redChannelOption.setIcons("/icons/red_channel_icon.png",
                "/icons/red_channel_selected_icon.png"
        );
    }

    private void createSepia() {
        ToolOption sepiaOption = new ToolOption("Sepia",
                new SepiaTool(), () -> cancelSelection(onCancel));
        sepiaOption.setOnSelect(tool -> select(tool, sepiaOption));
        tools.add(sepiaOption);
        sepiaOption.setIcons("/icons/sepia_icon.png",
                "/icons/sepia_selected_icon.png"
        );
    }

    private void createOrderedDithering() {
        ParameterToolOption ditheringOption = new ParameterToolOption("Ordered Kitov dithering",
                onCancel, List.of(
                        Parameters.builder().name("quantization red").min(2).max(128).initial(2)
                                .build(),
                        Parameters.builder().name("quantization green").min(2).max(128).initial(2)
                                .build(),
                        Parameters.builder().name("quantization blue").min(2).max(128).initial(2)
                                .build()
        ));
        ditheringOption.setOnToolSelect(tool -> select(tool, ditheringOption.getToolOption()));
        ditheringOption.setToolSupplier(() -> new OrderedDitheringTool(
                (int) ditheringOption.getParameter("quantization red"),
                (int) ditheringOption.getParameter("quantization green"),
                (int) ditheringOption.getParameter("quantization blue")
        ));
        tools.add(ditheringOption.getToolOption());
        ditheringOption.getToolOption().setIcons("/icons/dithering2_icon.png",
                "/icons/dithering2_selected_icon.png");
    }

    private void createFloydSteinbergDithering() {
        ParameterToolOption ditheringOption = new ParameterToolOption("Floyd-Steinberg Kitov dithering",
                onCancel, List.of(
                        Parameters.builder().name("red quantization").min(2).max(128).initial(2)
                                .build(),
                        Parameters.builder().name("green quantization").min(2).max(128).initial(2)
                                .build(),
                        Parameters.builder().name("blue quantization").min(2).max(128).initial(2)
                                .build()
        ));
        ditheringOption.setOnToolSelect(tool -> select(tool, ditheringOption.getToolOption()));
        ditheringOption.setToolSupplier(() -> new FloydSteinbergDitheringTool(
                (int) ditheringOption.getParameter("red quantization"),
                (int) ditheringOption.getParameter("green quantization"),
                (int) ditheringOption.getParameter("blue quantization")
        ));
        tools.add(ditheringOption.getToolOption());
        ditheringOption.getToolOption().setIcons("/icons/dithering1_icon.png",
                "/icons/dithering1_selected_icon.png");
    }

    private void createFloydSteinbergNazarovDithering() {
        ParameterToolOption ditheringOption = new ParameterToolOption("Floyd-Steinberg Nazarov dithering",
                onCancel, List.of(
                Parameters.builder().name("red quantization").min(2).max(128).initial(2)
                        .build(),
                Parameters.builder().name("green quantization").min(2).max(128).initial(2)
                        .build(),
                Parameters.builder().name("blue quantization").min(2).max(128).initial(2)
                        .build()
        ));
        ditheringOption.setOnToolSelect(tool -> select(tool, ditheringOption.getToolOption()));
        ditheringOption.setToolSupplier(() -> new FloydSteinbergNazarovTool(
                (int) ditheringOption.getParameter("red quantization"),
                (int) ditheringOption.getParameter("green quantization"),
                (int) ditheringOption.getParameter("blue quantization")
        ));
        tools.add(ditheringOption.getToolOption());
        ditheringOption.getToolOption().setIcons("/icons/dithering1_icon.png",
                "/icons/dithering1_selected_icon.png");
    }

    private void createOrderedNazarovDithering() {
        ParameterToolOption ditheringOption = new ParameterToolOption("Ordered Nazarov dithering",
                onCancel, List.of(
                Parameters.builder().name("red quantization").min(2).max(128).initial(2)
                        .build(),
                Parameters.builder().name("green quantization").min(2).max(128).initial(2)
                        .build(),
                Parameters.builder().name("blue quantization").min(2).max(128).initial(2)
                        .build()
        ));
        ditheringOption.setOnToolSelect(tool -> select(tool, ditheringOption.getToolOption()));
        ditheringOption.setToolSupplier(() -> new OrderedNazarovTool(
                (int) ditheringOption.getParameter("red quantization"),
                (int) ditheringOption.getParameter("green quantization"),
                (int) ditheringOption.getParameter("blue quantization")
        ));
        tools.add(ditheringOption.getToolOption());
        ditheringOption.getToolOption().setIcons("/icons/dithering2_icon.png",
                "/icons/dithering2_selected_icon.png");
    }

    private void createWave() {
        ParameterToolOption waveOption = new ParameterToolOption("Waves", onCancel,
                List.of(
                        Parameters.builder().name("height").min(0).max(100).initial(50)
                                .build()
        ));
        waveOption.setOnToolSelect(tool -> select(tool, waveOption.getToolOption()));
        waveOption.setToolSupplier(() -> new CircleWaveTool(
                (int) waveOption.getParameter("height")
        ));
        tools.add(waveOption.getToolOption());
        waveOption.getToolOption().setIcons("/icons/drop_waves_icon.png",
                "/icons/drop_waves_selected.png"
        );
    }

    private void createPixelArt() {
        ParameterToolOption pixelOption = new ParameterToolOption("Pixel art", onCancel,
                List.of(
                Parameters.builder().name("pixel size").min(1).max(50).initial(10)
                        .build()
        ));
        pixelOption.setOnToolSelect(tool -> select(tool, pixelOption.getToolOption()));
        pixelOption.setToolSupplier(() -> new PixelArtTool(
                (int) pixelOption.getParameter("pixel size")
        ));
        tools.add(pixelOption.getToolOption());
        pixelOption.getToolOption().setIcons("/icons/pixel_icon.png",
                "/icons/pixel_selected_icon.png"
        );
    }

    private void createWatercolor() {
        ToolOption watercolorOption = new ToolOption("Watercolor", new WatercolorTool(),
                () -> cancelSelection(onCancel));
        watercolorOption.setOnSelect(tool -> select(tool, watercolorOption));
        tools.add(watercolorOption);
        watercolorOption.setIcons("/icons/aqua_icon.png",
                "/icons/aqua_selected_icon.png");
    }

    private void createSharpness() {
        ToolOption sharpnessOption = new ToolOption("Increase sharpness",
                new SharpnessTool(), () -> cancelSelection(onCancel));
        sharpnessOption.setOnSelect(tool -> select(tool, sharpnessOption));
        tools.add(sharpnessOption);
        sharpnessOption.setIcons("/icons/sharpness_icon.png",
                "/icons/sharpness_selected_icon.png"
        );
    }

    private void createSobelHighlight() {
        ParameterToolOption highlightOption = new ParameterToolOption("Sobel border", onCancel,
                List.of(
                        Parameters.builder().name("binarization").min(0).max(200).initial(100)
                                .build()
                ));
        highlightOption.setOnToolSelect(tool -> select(tool, highlightOption.getToolOption()));
        highlightOption.setToolSupplier(() -> new SobelBorderHighlightTool(
                (int) highlightOption.getParameter("binarization")
        ));
        tools.add(highlightOption.getToolOption());
        highlightOption.getToolOption().setIcons("/icons/border2_icon.png",
                "/icons/border2_selected_icon.png"
        );
    }

    private void createRobertsHighlight() {
        ParameterToolOption highlightOption = new ParameterToolOption("Roberts border", onCancel,
                List.of(
                        Parameters.builder().name("binarization").min(0).max(500).initial(100)
                                .build()
                ));
        highlightOption.setOnToolSelect(tool -> select(tool, highlightOption.getToolOption()));
        highlightOption.setToolSupplier(() -> new RobertsBorderHighlightTool(
                (int) highlightOption.getParameter("binarization")
        ));
        tools.add(highlightOption.getToolOption());
        highlightOption.getToolOption().setIcons("/icons/border1_icon.png",
                "/icons/border1_selected_icon.png"
        );
    }

    private void createMedian() {
        ParameterToolOption medianOption = new ParameterToolOption("Median smooth", onCancel,
                List.of(
                        Parameters.builder().name("n").min(3).max(11).initial(3).step(2f)
                                .build()
        ));
        medianOption.setOnToolSelect(tool -> select(tool, medianOption.getToolOption()));
        medianOption.setToolSupplier(() -> new MedianSmoothTool(
                (int) medianOption.getParameter("n")
        ));
        tools.add(medianOption.getToolOption());
        medianOption.getToolOption().setIcons("/icons/median_smooth_icon.png",
                "/icons/median_smooth_selected_icon.png"
        );
    }

    private void createEmbossing() {
        ToolOption embossingOption = new ToolOption("Embossing",
                new EmbossingTool(), () -> cancelSelection(onCancel));
        embossingOption.setOnSelect(tool -> select(tool, embossingOption));
        tools.add(embossingOption);
        embossingOption.setIcons("/icons/embossing_icon.png",
                "/icons/embossing_selected_icon.png"
        );
    }

    private void createBlur() {
        ParameterToolOption blurOption = new ParameterToolOption("Gaussian blur", onCancel,
                List.of(
                        Parameters.builder().name("n").min(3).max(11).initial(3).step(2.0f)
                                .build(),
                        Parameters.builder().name("sigma").min(0.1f).max(20).initial(1)
                                .build()
        ));
        blurOption.setOnToolSelect(tool -> select(tool, blurOption.getToolOption()));
        blurOption.setToolSupplier(() -> new BlurTool(blurOption.getParameter("sigma"),
                (int) blurOption.getParameter("n")
        ));
        tools.add(blurOption.getToolOption());
        blurOption.getToolOption().setIcons("/icons/blur_icon.png",
                "/icons/blur_selected_icon.png"
        );
    }

    private void createGamma() {
        ParameterToolOption gammaOption = new ParameterToolOption("Gamma correction", onCancel,
                List.of(Parameters.builder().name("gamma").min(0.1f).max(10).initial(1)
                        .build()));
        gammaOption.setOnToolSelect(tool -> select(tool, gammaOption.getToolOption()));
        gammaOption.setToolSupplier(() -> new GammaTool(gammaOption.getParameter("gamma")));
        tools.add(gammaOption.getToolOption());
        gammaOption.getToolOption().setIcons("/icons/gamma_icon.png",
                "/icons/gamma_selected_icon.png"
        );
    }

    private void createInversion() {
        ToolOption inversionOption = new ToolOption("Inversion",
                new InversionTool(), () -> cancelSelection(onCancel));
        inversionOption.setOnSelect(tool -> select(tool, inversionOption));
        tools.add(inversionOption);
        inversionOption.setIcons("/icons/inversion_icon.png",
                "/icons/inversion_selected_icon.png"
        );
    }

    private void createBlackWhite() {
        ToolOption blackWhiteOption = new ToolOption("Black white",
                new BlackWhiteTool(), () -> cancelSelection(onCancel));
        blackWhiteOption.setOnSelect(tool -> select(tool, blackWhiteOption));
        tools.add(blackWhiteOption);
        blackWhiteOption.setIcons("/icons/black_white_icon.png",
                "/icons/black_white_selected_icon.png"
        );
    }

    public void cancelSelection() {
        toolBarGroup.forEach(b -> b.setSelected(false));
        menuBarGroup.forEach(b -> b.setSelected(false));
    }

    private void cancelSelection(Runnable onCancel) {
        cancelSelection();
        onCancel.run();
    }

}
