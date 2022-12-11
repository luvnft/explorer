import 'package:explorer_ui/src/widgets/default_app_bar.dart';
import 'package:explorer_ui/src/widgets/default_bottom_navigation_bart.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

// About page.
// - Logo
// - explanation
class AboutScreen extends ConsumerWidget {
  // Constructor.
  const AboutScreen({super.key});

  // The framework calls this method when this widget is inserted into the tree in a given BuildContext and when the dependencies of this widget change.
  // The framework replaces the subtree below this widget with the widget returned by this method
  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return Scaffold(
      // =====================================================================
      // An app bar consists of a toolbar and potentially other widgets.
      appBar: defaultRoylloAppBar(context),

      // =====================================================================
      // The page content.
      // A widget that centers its child within itself
      body: Center(
        // A widget that displays its children in a vertical array.
          child: Column(
            // X and Y alignments.
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              // Royllo icon.
              Image.asset('assets/images/logo_large_with_text.png'),
              // TODO Project description
            ],
          )),

      // =====================================================================
      // Bottom navigation bar.
      bottomNavigationBar: defaultRoylloBottomNavigationBar(context),
    );
  }
}
