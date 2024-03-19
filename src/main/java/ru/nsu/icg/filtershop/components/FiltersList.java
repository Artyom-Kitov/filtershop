package ru.nsu.icg.filtershop.components;

import lombok.Getter;
import ru.nsu.icg.filtershop.model.tools.*;

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
    private final ButtonGroup toolBarGroup;
    private final ButtonGroup menuBarGroup;

    public FiltersList(Consumer<? super Tool> onToolSelect,
                       Runnable onCancel,
                       FiltershopToolBar mainToolBar) {
        toolBar = mainToolBar;
        toolsMenu = new JMenu("Filter");
        tools = new ArrayList<>();

        createBlackWhite(onToolSelect, onCancel);
        createInversion(onToolSelect, onCancel);
        createGamma(onToolSelect, onCancel);
        createBlur(onToolSelect, onCancel);
        createEmbossing(onToolSelect, onCancel);
        createMedian(onToolSelect, onCancel);
        createBorderHighlight(onToolSelect, onCancel);
        createSharpness(onToolSelect, onCancel);
        createWatercolor(onToolSelect, onCancel);
        createPixelArt(onToolSelect, onCancel);
        createWave(onToolSelect, onCancel);
        createFloydSteinbergDithering(onToolSelect, onCancel);

        toolBarGroup = new ButtonGroup();
        menuBarGroup = new ButtonGroup();
        for (ToolOption tool : tools) {
            toolBar.add(tool.getRadioButton());
            toolBarGroup.add(tool.getRadioButton());

            toolsMenu.add(tool.getMenuItem());
            menuBarGroup.add(tool.getMenuItem());
        }
    }

    private void createFloydSteinbergDithering(Consumer<? super Tool> onToolSelect, Runnable onCancel) {
        ParameterToolOption ditheringOption = new ParameterToolOption("Floyd-Steinberg dithering",
                onToolSelect, onCancel, List.of(
                        Parameters.builder().name("red quantization").min(2).max(128).initial(2).warning("invalid red quantization")
                                .build(),
                        Parameters.builder().name("green quantization").min(2).max(128).initial(2).warning("invalid green quantization")
                                .build(),
                        Parameters.builder().name("blue quantization").min(2).max(128).initial(2).warning("invalid blue quantization")
                                .build()
        ));
        ditheringOption.setToolSupplier(() -> new FloydSteinbergDitheringTool(
                (int) ditheringOption.getParameter("red quantization"),
                (int) ditheringOption.getParameter("green quantization"),
                (int) ditheringOption.getParameter("blue quantization")
        ));
        tools.add(ditheringOption.getToolOption());
        ditheringOption.getToolOption().setIcons("/icons/dithering1_icon.png",
                "/icons/dithering1_selected_icon.png");
    }

    private void createWave(Consumer<? super Tool> onToolSelect, Runnable onCancel) {
        ParameterToolOption waveOption = new ParameterToolOption("Waves", onToolSelect,
                onCancel, List.of(
                        Parameters.builder().name("height").min(0).max(100).initial(50).warning("invalid height")
                                .build()
        ));
        waveOption.setToolSupplier(() -> new CircleWaveTool(
                (int) waveOption.getParameter("height")
        ));
        tools.add(waveOption.getToolOption());
        waveOption.getToolOption().setIcons("/icons/drop_waves_icon.png",
                "/icons/drop_waves_selected.png"
        );
    }

    private void createPixelArt(Consumer<? super Tool> onToolSelect, Runnable onCancel) {
        ParameterToolOption pixelOption = new ParameterToolOption("Pixel art", onToolSelect,
                onCancel, List.of(
                Parameters.builder().name("pixel size").min(0).max(50).initial(10).warning("invalid pixel size")
                        .build()
        ));
        pixelOption.setToolSupplier(() -> new PixelArtTool(
                (int) pixelOption.getParameter("pixel size")
        ));
        tools.add(pixelOption.getToolOption());
        pixelOption.getToolOption().setIcons("/icons/pixel_icon.png",
                "/icons/pixel_selected_icon.png"
        );
    }

    private void createWatercolor(Consumer<? super Tool> onToolSelect, Runnable onCancel) {
        ToolOption watercolorOption = new ToolOption("Watercolor", new WatercolorTool(),
                onToolSelect, () -> cancelSelection(onCancel));
        tools.add(watercolorOption);
        watercolorOption.setIcons("/icons/aqua_icon.png",
                "/icons/aqua_selected_icon.png");
    }

    private void createSharpness(Consumer<? super Tool> onToolSelect, Runnable onCancel) {
        ToolOption sharpnessOption = new ToolOption("Increase sharpness",
                new SharpnessTool(), onToolSelect, () -> cancelSelection(onCancel));
        tools.add(sharpnessOption);
        sharpnessOption.setIcons("/icons/sharpness_icon.png",
                "/icons/sharpness_selected_icon.png"
        );
    }

    private void createBorderHighlight(Consumer<? super Tool> onToolSelect, Runnable onCancel) {
        ToolOption highlightOption = new ToolOption("Border highlight",
                new BorderHighlightTool(), onToolSelect, () -> cancelSelection(onCancel));
        tools.add(highlightOption);
        highlightOption.setIcons("/icons/border1_icon.png",
                "/icons/border1_selected_icon.png"
        );
    }

    private void createMedian(Consumer<? super Tool> onToolSelect, Runnable onCancel) {
        ParameterToolOption medianOption = new ParameterToolOption("Median smooth", onToolSelect,
                onCancel, List.of(
                        Parameters.builder().name("n").min(3).max(11).initial(3).step(2f).warning("invalid n")
                                .build()
        ));
        medianOption.setToolSupplier(() -> new MedianSmoothTool(
                (int) medianOption.getParameter("n")
        ));
        tools.add(medianOption.getToolOption());
        medianOption.getToolOption().setIcons("/icons/median_smooth_icon.png",
                "/icons/median_smooth_selected_icon.png"
        );
    }

    private void createEmbossing(Consumer<? super Tool> onToolSelect, Runnable onCancel) {
        ToolOption embossingOption = new ToolOption("Embossing",
                new EmbossingTool(), onToolSelect, () -> cancelSelection(onCancel));
        tools.add(embossingOption);
        embossingOption.setIcons("/icons/embossing_icon.png",
                "/icons/embossing_selected_icon.png"
        );
    }

    private void createBlur(Consumer<? super Tool> onToolSelect, Runnable onCancel) {
        ParameterToolOption blurOption = new ParameterToolOption("Gaussian blur", onToolSelect,
                onCancel, List.of(
                        Parameters.builder().name("n").min(3).max(11).initial(3).step(2.0f).warning("invalid n")
                                .build(),
                        Parameters.builder().name("sigma").min(0.1f).max(20).initial(1).warning("invalid standard deviation")
                                .build()
        ));
        blurOption.setToolSupplier(() -> new BlurTool(blurOption.getParameter("sigma"),
                (int) blurOption.getParameter("n")
        ));
        tools.add(blurOption.getToolOption());
        blurOption.getToolOption().setIcons("/icons/blur_icon.png",
                "/icons/blur_selected_icon.png"
        );
    }

    private void createGamma(Consumer<? super Tool> onToolSelect, Runnable onCancel) {
        ParameterToolOption gammaOption = new ParameterToolOption("Gamma correction", onToolSelect, onCancel,
                List.of(Parameters.builder().name("gamma red").min(0.1f).max(10).initial(1).warning("invalid red gamma value")
                        .build(),
                Parameters.builder().name("gamma green").min(0.1f).max(10).initial(1).warning("invalid green gamma value")
                        .build(),
                Parameters.builder().name("gamma blue").min(0.1f).max(10).initial(1).warning("invalid blue gamma value")
                        .build()));
        gammaOption.setToolSupplier(() -> new GammaTool(gammaOption.getParameter("gamma red"),
                gammaOption.getParameter("gamma green"),
                gammaOption.getParameter("gamma blue")));
        tools.add(gammaOption.getToolOption());
        gammaOption.getToolOption().setIcons("/icons/gamma_icon.png",
                "/icons/gamma_selected_icon.png"
        );
    }

    private void createInversion(Consumer<? super Tool> onToolSelect, Runnable onCancel) {
        ToolOption inversionOption = new ToolOption("Inversion",
                new InversionTool(), onToolSelect, () -> cancelSelection(onCancel));
        tools.add(inversionOption);
        inversionOption.setIcons("/icons/inversion_icon.png",
                "/icons/inversion_selected_icon.png"
        );
    }

    private void createBlackWhite(Consumer<? super Tool> onToolSelect, Runnable onCancel) {
        ToolOption blackWhiteOption = new ToolOption("Black white",
                new BlackWhiteTool(), onToolSelect, () -> cancelSelection(onCancel));
        tools.add(blackWhiteOption);
        blackWhiteOption.setIcons("/icons/black_white_icon.png",
                "/icons/black_white_selected_icon.png"
        );
    }

    private void cancelSelection(Runnable onCancel) {
        toolBarGroup.clearSelection();
        menuBarGroup.clearSelection();
        onCancel.run();
    }

}
