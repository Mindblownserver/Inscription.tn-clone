<template>
    <div class="container">
        <Card id="card">
            <template #content>
                <div class="card">
                    <Accordion value="0">
                        <AccordionPanel value="0">
                            <AccordionHeader>Comment peux-je créer un compte?</AccordionHeader>
                            <AccordionContent>
                                <p class="content">
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                                    consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                                </p>
                            </AccordionContent>
                        </AccordionPanel>
                        <AccordionPanel value="1">
                            <AccordionHeader>Comment peux-je recouvrir mon mot de passe si je l'ai oublié</AccordionHeader>
                            <AccordionContent>
                                <p class="content">
                                    Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim
                                    ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Consectetur, adipisci velit, sed quia non numquam eius modi.
                                </p>
                            </AccordionContent>
                        </AccordionPanel>
                        <AccordionPanel value="2">
                            <AccordionHeader>Quelles cartes sont-elles acceptées</AccordionHeader>
                            <AccordionContent>
                                <p class="content"  >
                                    At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa
                                    qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus.
                                </p>
                            </AccordionContent>
                        </AccordionPanel>
                    </Accordion>
                </div>
            </template>
        </Card>
    </div>
    
</template>

<script setup lang="ts">

import Accordion from 'primevue/accordion';
import AccordionPanel from 'primevue/accordionpanel';
import AccordionHeader from 'primevue/accordionheader';
import AccordionContent from 'primevue/accordioncontent';
import Card from 'primevue/card';
</script>

<style>
#card{
    grid-column: 2/3;
}
.container{
    margin-top: 10px;
    display: grid;
    grid-template-columns: 270px 1fr 190px;
}
.content{
    margin: 0;
    text-align: start;
}
</style>