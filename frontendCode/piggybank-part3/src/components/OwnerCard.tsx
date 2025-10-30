import {Card, CardActionArea, CardContent, CardMedia, Typography} from "@mui/material"
import type {Owner} from "../model/owner.ts"

interface OwnerCardProps {
    owner: Owner
}

export function OwnerCard({owner}: OwnerCardProps) {
    return (
        <Card sx={{width: 200}} variant="outlined">
            <CardActionArea>
                <CardMedia component="img" sx={{height: 280, objectFit: "cover", p: 1, objectPosition: "top"}}
                           image={`/img/users/${owner.id}.png`}
                           alt="piggybank owner"/>
                <CardContent>
                    <Typography gutterBottom variant="h4" component="div" sx={{textAlign: 'center'}}>
                        {owner.name}
                    </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    )
}