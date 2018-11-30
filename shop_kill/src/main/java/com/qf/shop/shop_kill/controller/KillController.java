package com.qf.shop.shop_kill.controller;

import com.qf.entity.Kill;
import com.qf.shop.shop_kill.service.IKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author ken
 * @Time 2018/11/30 9:17
 * @Version 1.0
 */
@Controller
@RequestMapping("/kill")
public class KillController {

    @Autowired
    private IKillService killService;

    @RequestMapping("/querykill")
    public String queryKill(Integer gid, Model model){

        Kill kill = killService.queryKillInfo(gid);
        model.addAttribute("kill", kill);

        return "killinfo";
    }


    @RequestMapping("/gokill")
    public String goKill(Integer gid, Integer gnumber){
        for(int i = 1; i <= 20000; i++){
            new Thread(){
                @Override
                public void run() {
                    killService.kill(gid, gnumber, 1);
                }
            }.start();
        }
        return "succ";
    }
}
