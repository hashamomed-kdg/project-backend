import {alpha, createTheme} from "@mui/material"
import {brand, gray} from "./colors.ts"

export const theme = createTheme({
    colorSchemes: {
        dark: {
            palette: {
                primary: {
                    contrastText: brand[50],
                    light: brand[300],
                    main: brand[400],
                    dark: brand[700],
                },
                divider: alpha(gray[700], 0.6),
                background: {
                    default: gray[900],
                    paper: gray[800]
                },
                text: {
                    primary: brand[50],
                    secondary: gray[400],
                },
            },
        },
        light: {
            palette: {
                primary: {
                    light: brand[200],
                    main: brand[400],
                    dark: brand[700],
                    contrastText: brand[50],
                },
                divider: alpha(gray[300], 0.4),
                background: {
                    default: 'hsl(0, 0%, 99%)',
                    paper: gray[50]
                },
                text: {
                    primary: brand[800],
                    secondary: gray[600],
                },
            },
        },
    },
    typography: {
        fontFamily: '"Inter", "Roboto", "Helvetica", "Arial", sans-serif',  // Set Inter as the default font
    },
})